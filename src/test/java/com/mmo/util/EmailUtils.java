package com.mmo.util;

import org.testng.Assert;
import org.testng.Reporter;

import javax.mail.*;
import javax.mail.search.SubjectTerm;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility for interacting with an Email application
 */
public class EmailUtils extends BaseClass {

    private Folder folder;
    static Session session;
    static Store store;

    public enum EmailFolder {
        INBOX("Inbox"),
        STARTUSINGMMO("startUsingMMO"),
        STARTUSINGGEOTAX("startUsingGeoTAX"),
        PLANCHANGE("planChange"),
        SUBUSERS("subUsers"),
        JOBSUCCESS("jobSuccess"),
        JOBFAIL("jobFail"),
        SPAM("SPAM");


        private String text;

        private EmailFolder(String text){
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    /**
     * Uses email.username and email.password properties from the properties file. Reads from Inbox folder of the email application
     * @throws MessagingException
     */
    public EmailUtils() throws MessagingException {
        this(EmailFolder.INBOX);
    }

    /**
     * Uses username and password in properties file to read from a given folder of the email application
     * @param emailFolder Folder in email application to interact with
     * @throws MessagingException
     */
    public EmailUtils(EmailFolder emailFolder) throws MessagingException {
        this(getEmailUsernameFromProperties(),
                getEmailPasswordFromProperties(),
                getEmailServerFromProperties(),
                emailFolder);
    }

    /**
     * Connects to email server with credentials provided to read from a given folder of the email application
     * @param username Email username (e.g. janedoe@email.com)
     * @param password Email password
     * @param server Email server (e.g. smtp.email.com)
     * @param emailFolder Folder in email application to interact with
     */
    public EmailUtils(String username, String password, String server, EmailFolder emailFolder) throws MessagingException {
        Properties props = System.getProperties();
        try {
            //props.load(new FileInputStream(new File("D:/MMOnline/Automation/workspace/tests/src/test/resources/email.properties")));
            props.load(this.getClass().getClassLoader().getResourceAsStream("email.properties"));
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        session = Session.getInstance(props);
        store = session.getStore("imaps");
        store.connect(server, username, password);
    }

    public static void storeClose() throws MessagingException {
        store.close();
    }

    //************* GET EMAIL PROPERTIES *******************

    public static String getEmailAddressFromProperties(){
        return System.getProperty("email.address");
    }

    public static String getEmailUsernameFromProperties(){
        return System.getProperty("email.username");
    }

    public static String getEmailPasswordFromProperties(){
        return System.getProperty("email.password");
    }

    public static String getEmailProtocolFromProperties(){
        return System.getProperty("email.protocol");
    }

    public static int getEmailPortFromProperties(){
        return Integer.parseInt(System.getProperty("email.port"));
    }

    public static String getEmailServerFromProperties(){
        return System.getProperty("email.server");
    }




    //************* EMAIL ACTIONS *******************

    public void openEmail(Message message) throws Exception{
        message.getContent();
    }

    public int getNumberOfMessages() throws MessagingException {
        return folder.getMessageCount();
    }

    public int getNumberOfUnreadMessages()throws MessagingException {
        return folder.getUnreadMessageCount();
    }

    public int getNumberOfUnreadMessagesByFolder(EmailFolder emailFolder) throws MessagingException {
        folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);
        return folder.getUnreadMessageCount();
    }

    /**
     * Gets a message by its position in the folder. The earliest message is indexed at 1.
     */
    public Message getMessageByIndex(int index) throws MessagingException {
        return folder.getMessage(index);
    }

    public Message getLatestMessage() throws MessagingException{
        return getMessageByIndex(getNumberOfMessages());
    }

    /**
     * Gets all messages within the folder
     */
    public Message[] getAllMessages() throws MessagingException {
        return folder.getMessages();
    }

    /**
     * @param maxToGet maximum number of messages to get, starting from the latest. For example, enter 100 to get the last 100 messages received.
     */
    public Message[] getMessages(int maxToGet) throws MessagingException {
        Map<String, Integer> indices = getStartAndEndIndices(maxToGet);
        return folder.getMessages(indices.get("startIndex"), indices.get("endIndex"));
    }

    /**
     * Searches for messages with a specific subject
     * @param subject Subject to search messages for
     * @param unreadOnly Indicate whether to only return matched messages that are unread
     * @param maxToSearch maximum number of messages to search, starting from the latest. For example, enter 100 to search through the last 100 messages.
     */
    public Message[] getMessagesBySubject(String subject, boolean unreadOnly, int maxToSearch) throws Exception{
        Map<String, Integer> indices = getStartAndEndIndices(maxToSearch);

        Message messages[] = folder.search(
                new SubjectTerm(subject),
                folder.getMessages(indices.get("startIndex"), indices.get("endIndex")));

        if(unreadOnly){
            List<Message> unreadMessages = new ArrayList<Message>();
            for (Message message : messages) {
                if(isMessageUnread(message)) {
                    unreadMessages.add(message);
                }
            }
            messages = unreadMessages.toArray(new Message[]{});
        }

        return messages;
    }

    /**
     * Searches for messages with a specific subject
     * @param subject Subject to search messages for
     * @param unreadOnly Indicate whether to only return matched messages that are unread
     * @param maxToSearch maximum number of messages to search, starting from the latest. For example, enter 100 to search through the last 100 messages.
     */
    public Message[] getMessagesBySubjectInFolder(String subject, boolean unreadOnly, int maxToSearch, EmailFolder emailFolder) throws Exception{
        folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);
        Map<String, Integer> indices = getStartAndEndIndices(maxToSearch);

        Message messages[] = folder.search(
                new SubjectTerm(subject),
                folder.getMessages(indices.get("startIndex"), indices.get("endIndex")));

        if(unreadOnly){
            List<Message> unreadMessages = new ArrayList<Message>();
            for (Message message : messages) {
                if(isMessageUnread(message)) {
                    unreadMessages.add(message);
                }
            }
            messages = unreadMessages.toArray(new Message[]{});
        }
        return messages;
    }

    /**
     * Returns HTML of the email's content
     */
    public String getMessageContent(Message message) throws Exception {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(message.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }

    /**
     * Returns all urls from an email message with the linkText specified
     */
    public List<String> getUrlsFromMessage(Message message, String linkText) throws Exception{
        String html = getMessageContent(message);
        List<String> allMatches = new ArrayList<String>();
        Matcher matcher = Pattern.compile("(<a [^>]+>)"+linkText+"</a>").matcher(html);
        while (matcher.find()) {
            String aTag = matcher.group(1);
            allMatches.add(aTag.substring(aTag.indexOf("toke=n=3D"), aTag.indexOf("\">")));
        }
        return allMatches;
    }

    private Map<String, Integer> getStartAndEndIndices(int max) throws MessagingException {
        int endIndex = getNumberOfMessages();
        int startIndex = endIndex - max;

        //In event that maxToGet is greater than number of messages that exist
        if(startIndex < 1){
            startIndex = 1;
        }

        System.out.println("endIndex: " + endIndex);
        System.out.println("Startindex: " + startIndex);

        Map<String, Integer> indices = new HashMap<String, Integer>();
        indices.put("startIndex", startIndex);
        indices.put("endIndex", endIndex);

        return indices;
    }

    /**
     * Gets text from the end of a line.
     * In this example, the subject of the email is 'Authorization Code'
     * And the line to get the text from begins with 'Authorization code:'
     * Change these items to whatever you need for your email. This is only an example.
     */
    public String getAuthorizationCode() throws Exception {
        Message email = getMessagesBySubject("Authorization Code", true, 5)[0];
        BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));

        String line;
        String prefix = "Authorization code:";

        while ((line = reader.readLine()) != null) {
            if(line.startsWith(prefix)) {
                return line.substring(line.indexOf(":") + 1);
            }
        }
        return null;
    }

    public void isTextPresentInMessage(String emailSubject, String userID, String textInMessage[], EmailFolder emailFolder) throws Exception {
        folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);
        Message email = getMessagesBySubject(emailSubject, true, 1)[0];
        Assert.assertEquals(email.getAllRecipients()[0].toString().toLowerCase(), userID.toLowerCase(),
                "Recipient incorrect; expected: " + userID + " but actual: " + email.getAllRecipients()[0].toString());
        for (String text : textInMessage){
            System.out.println("**TXT: **" + text + "\n");
            Assert.assertTrue(emailUtils.isTextInMessage(email, text), "'" + text + "' is not found");;
        }
        folder.close(true);
    }

    /**
     * LEARNING
     *
     * @param emailSubject
     * @param emailFolder
     * @throws MessagingException
     * @throws IOException
     */
    public void getEmail(String emailSubject, EmailFolder emailFolder) throws MessagingException, IOException {
        folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);
        System.out.println("**getMessagesLength**: " + folder.getMessages().length + "\n");
        Message[] abc = folder.getMessages(8, 10);
        for(Message m : abc){
            System.out.println("**getMessageNumber**: " + m.getMessageNumber() + "\n");
            System.out.println("**getAllRecipientsLength**: " + m.getAllRecipients().length + "\n");
            System.out.println("**getAllRecipients**: " + m.getAllRecipients()[0].toString() + "\n");
            System.out.println("**getFromLength**: " + m.getFrom().length + "\n");
            System.out.println("**getFrom**: " + m.getFrom()[0].toString() + "\n");
            System.out.println("**getSubject**: " + m.getSubject() + "\n");
        }
    }

    /**
     * Gets one line of text
     * In this example, the subject of the email is 'Authorization Code'
     * And the line preceding the code begins with 'Authorization code:'
     * Change these items to whatever you need for your email. This is only an example.
     */
    public String getVerificationCode() throws Exception {
        Message email = getMessagesBySubject("Authorization Code", true, 5)[0];
        BufferedReader reader = new BufferedReader(new InputStreamReader(email.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            if(line.startsWith("Authorization code:")) {
                return reader.readLine();
            }
        }
        return null;
    }

    public void markAllEmailsAsUnread(EmailFolder emailFolder) throws Exception {
        //folder = store.getDefaultFolder().getFolder("[Gmail]/All Mail");
        folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);
        int countAllEmails = getNumberOfMessages();
        System.out.println("countAllEmails: " + countAllEmails);
        int countUnreadEmails = getNumberOfUnreadMessages();
        System.out.println("countUnreadEmails: " + countUnreadEmails);
        Message[] m = folder.getMessages();

        if(countUnreadEmails > 0){
            System.out.println("FolderName: " + emailFolder.getText());
            for (int i = 0; i < countAllEmails; i++) {
                m[i].setFlag(Flags.Flag.SEEN, true);
                System.out.println("i: " + i);
            }
        }
        folder.close(true);
    }

    public void deleteAllEmails(EmailFolder emailFolder) throws Exception {
        //folder = store.getDefaultFolder().getFolder("[Gmail]/All Mail");
        folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);
        int countAllEmails = getNumberOfMessages();
        System.out.println("countAllEmails " + emailFolder.getText() + " :" + countAllEmails );
        Message[] m = folder.getMessages();

        if(countAllEmails > 0){
            for (int i = 0; i < countAllEmails; i++) {
                m[i].setFlag(Flags.Flag.DELETED, true);
                System.out.println("i: " + i);
            }
        }
        folder.close(true);
    }

    public Boolean waitForEmailReceived(String emailSubject, EmailFolder emailFolder, int unreadEmails) throws Exception {
        Boolean emailsReceived = null;
        int z = 0;
        int unreadCount = 0;
        int check = 0;
        while (z < 13) {
            do{
                try{
                    unreadCount = getNumberOfUnreadMessagesByFolder(emailFolder);
                    check = 5;
                }catch (Exception e){
                    System.out.print("****IN EMAIL EXCPTN: ****" + "\n");
                    new EmailUtils("mmoautomated@gmail.com", "Precisely@123","smtp.gmail.com", EmailUtils.EmailFolder.STARTUSINGMMO);
                    check++;
                }
            }while(check < 5);
            System.out.print("****UNREAD: ****" + unreadCount + "\n");
            if (unreadEmails == unreadCount) {
                System.out.print("****INSIDE IF: ****" + unreadCount + "\n");
                emailsReceived = true;
                z = 13;
            }else{
                System.out.print("****INSIDE ELSE Z= " + z + "\n");
                z++;
                Thread.sleep(5000);
                if (z == 13) {
                    folder.close(true);
                    u.illegalStateException("Email with subject: " + emailSubject + " not found after multiple wait");
                }
            }
        }
        folder.close(true);
        return emailsReceived;
    }

    public String getToken(String emailSubject, String userID, EmailFolder emailFolder) throws Exception {
        folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);
        Message email = getMessagesBySubject(emailSubject, true, 1)[0];
        String html = getMessageContent(email);
        System.out.println("**html**: " + html);
        if(!html.contains("You're almost= ready to start using " + emailSubject.substring(28) + ". Complete your registration using this emai=l address: " + userID.toLowerCase()))
        {
            u.illegalStateException("Complete your registration using this email address is not found: "+ userID);
        }

        int	preIndex = 0;
        if(envValue.equalsIgnoreCase("qa"))
        {
            preIndex = html.indexOf("token=3D");
        } else if(envValue.equalsIgnoreCase("ppd"))
        {
            System.out.print("****OPEN PPD URL****");
            driver.get("https://" + xpv.getTokenValue("ppdURL"));
        } else {
            preIndex = html.indexOf("=oken=3D");
        }

        int	  searchIndex = preIndex + html.substring(preIndex).indexOf("\" style");

        System.out.println("preIndex: " + preIndex);
        System.out.println("searchIndex: " + searchIndex);
        String token = html.substring(preIndex + 8, searchIndex);
        System.out.println("token: " + token);
        folder.close(true);
        return token;
    }

    public String getTokenForSubUsers(String emailSubject, String userID, EmailFolder emailFolder) throws Exception {
        folder = store.getFolder(emailFolder.getText());
        folder.open(Folder.READ_WRITE);
        Message email = getMessagesBySubject(emailSubject, true, 1)[0];
        String html = getMessageContent(email);
        System.out.println("**html**: " + html);
        if(!html.contains("Your access to MapMarker has been granted. All you need to do is register with your email address: " + userID))
        {
            u.illegalStateException("Complete your registration for subUser's using this email address is not found: "+ userID);
        }

        int	preIndex = 0;
        if(envValue.equalsIgnoreCase("qa"))
        {
            preIndex = html.indexOf("token=");
        } else if(envValue.equalsIgnoreCase("ppd"))
        {
            System.out.print("****OPEN PPD URL****");
            driver.get("https://" + xpv.getTokenValue("ppdURL"));
        } else {
            preIndex = html.indexOf("=oken=3D");
        }

        int	  searchIndex = preIndex + html.substring(preIndex).indexOf("\" style");

        System.out.println("preIndex: " + preIndex);
        System.out.println("searchIndex: " + searchIndex);
        String token = html.substring(preIndex + 6, searchIndex);
        System.out.println("token: " + token);
        folder.close(true);
        return token;
    }

    public String getJobTokenFromEmail(Message email, String findText) throws Exception {
        String html = getMessageContent(email);
        System.out.println("**html**: " + html);
        int	preIndex = 0;
        if(envValue.equalsIgnoreCase("qa"))
        {
            preIndex = html.indexOf(findText);
        } else if(envValue.equalsIgnoreCase("ppd"))
        {
            System.out.print("****OPEN PPD URL****");
            driver.get("https://" + xpv.getTokenValue("ppdURL"));
        } else {
            preIndex = html.indexOf("=oken=3D");
        }

        int	  searchIndex = preIndex + html.substring(preIndex).indexOf("\" style");

        String token = html.substring(preIndex + 40, searchIndex);
        String check = "=";
        if(token.contains(check))
        {
            token = token.replaceAll(check, "");
        }
        System.out.println("token: " + token);
        return token;
    }

    public Boolean testVerifyJobCompleteEmailAndAccessDetailsDirectly(String outFileName, String userID, boolean outFileFound, String download) throws Exception {
        List<Message> list = new ArrayList<>(Arrays.asList(Emails));
        for (Message email : Emails) {
            Boolean fileFoundInEmail;

//            if(download.equalsIgnoreCase("N")){
//                fileFoundInEmail = emailUtils.isTextInMessage(email, outFileName.substring(0, 15) + "=" + outFileName.substring(15));
//            }else{
//                fileFoundInEmail = emailUtils.isTextInMessage(email, outFileName);
//            }

//          System.out.println("**EMAIL ID: **" + email.getAllRecipients()[0].toString() + "\n");
            System.out.println("**user ID: **" + userID + "\n");
            System.out.println("**File: **" + outFileName + "\n");

            fileFoundInEmail = emailUtils.isTextInMessage(email, outFileName);

            if(fileFoundInEmail)
            {
                System.out.println("**File Found**" + "\n");
//                Assert.assertEquals(email.getAllRecipients()[0].toString().toLowerCase(), userID.toLowerCase(),
//                        "Recipient incorrect; expected: " + userID + " but actual: " + email.getAllRecipients()[0].toString());
                outFileFound = true;
                Reporter.log("Job success email received for : " + outFileName + "<br/>", true);
                if(download.equalsIgnoreCase("Y")){
                    u.emptyDefaultDownloadPath(defaultDownloadPath);
                    String downloadTemplate = null;
                    String zipActualFileName = null;
//                    String jobTokenFromEmail = emailUtils.getJobTokenFromEmail(email, "mapmarker-qa.li.=precisely.services?p=3D");
//                    driver.get("https://mandrillapp.com/track/click/30875726/mapmarker-qa.li.precisely.services?p=" + jobTokenFromEmail);

                    if(outFileName.contains("Forward_CSV_")){
                        downloadTemplate = "MapMarker_Geocoding_Template.csv";
                        Assert.assertTrue(emailUtils.isTextInMessage(email, downloadTemplate), downloadTemplate + " is not in email");
                        zipActualFileName = downloadTemplate;
                    }else if(outFileName.contains("Forward_TAB_")){
                        downloadTemplate = "MapMarker_Geocoding_Template_TAB.zip";
                        Assert.assertTrue(emailUtils.isTextInMessage(email, downloadTemplate), downloadTemplate + " is not in email");
                        zipActualFileName = downloadTemplate;
                    }else if(outFileName.contains("Forward_SHP_")){
                        downloadTemplate = "MapMarker_Geocoding_Template_SHP.zip";
                        Assert.assertTrue(emailUtils.isTextInMessage(email, downloadTemplate), downloadTemplate + " is not in email");
                        zipActualFileName = downloadTemplate;
                    }else if(outFileName.contains("Reverse_CSV_")){
                        downloadTemplate = "MapMarker_ReverseGeocoding_Template.csv";
                        Assert.assertTrue(emailUtils.isTextInMessage(email, downloadTemplate), downloadTemplate + " is not in email");
                        zipActualFileName = downloadTemplate;
                    }else if(outFileName.contains("Reverse_NonExtTAB_")){
                        downloadTemplate = "MapMarker_ReverseGeocoding_Template_TAB.zip";
                        Assert.assertTrue(emailUtils.isTextInMessage(email, downloadTemplate), downloadTemplate + " is not in email");
                        zipActualFileName = downloadTemplate;
                    }else{
                        downloadTemplate = "MapMarker_ReverseGeocoding_Template_SHP.zip";
                        Assert.assertTrue(emailUtils.isTextInMessage(email, downloadTemplate), downloadTemplate + " is not in email");
                        zipActualFileName = downloadTemplate;
                    }

                    driver.get("https://mapmarker-qa.li.precisely.services/assets/" + downloadTemplate);
                    if(u.isFileDownloaded(defaultDownloadPath, zipActualFileName)) {
                        Reporter.log("Successfully accessed Email & Downloaded input file template for fail job: " + zipActualFileName + "<br/>",
                                true);
                    }else{
                        Assert.fail("Unable to download file fully until 2 mins: " + zipActualFileName);
                    }
                }
                list.remove(email);
                Emails = list.toArray(new Message[0]);
                System.out.println("Inside Email LENGTH: " + Emails.length);
                break;
            }
        }
        return outFileFound;
    }

    //************* BOOLEAN METHODS *******************
    /**
     * Searches an email message for a specific string
     */
    public boolean isTextInMessage(Message message, String text) throws Exception {
        String content = getMessageContent(message);

        //Some Strings within the email have whitespace and some have break coding. Need to be the same.
        content = content.replace("&nbsp;", " ");
        System.out.println("CONTENT: " + content);
        return content.contains(text);
    }

    public boolean isMessageInFolder(String subject, boolean unreadOnly) throws Exception {
        int messagesFound = getMessagesBySubject(subject, unreadOnly, getNumberOfMessages()).length;
        return messagesFound > 0;
    }

    public boolean isMessageUnread(Message message) throws Exception {
        return !message.isSet(Flags.Flag.SEEN);
    }
}