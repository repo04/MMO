package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import com.mmo.util.EmailUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JobsBySATests extends BaseClass {
    private Actions a = new Actions();
    static String[][] saOutFileNamesArray = new String[2][1];
    int x = 0;
    Boolean check=true;
    int abc = 1;
    Message[] Emails = null;

    @DataProvider(name = "SAJobs")
    public static Object[][] SAJobs(ITestContext context) throws Exception {
        Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "executeJobs", "SAJobs");
        return (retObjArr);
    }

    @DataProvider(name = "SAIdAndInputJobDetails")
    public static Iterator<Object[]> SAIdAndInputJobDetails(ITestContext context) throws Exception {
        System.out.println("init SAIdAndInputJobDetails");
        return DataProviderUtility.cartesianProviderFrom(SignUp.FreeUSUserDetails(context), SAJobs(context));
    }

    @DataProvider(name = "SAOutFileNames")
    public static Object[][] SAOutFileNames(ITestContext context) throws Exception {
        System.out.println("init SAOutFileNames");
        return saOutFileNamesArray;
    }

    @DataProvider(name = "checkValidations")
    public static Object[][] checkValidations(ITestContext context) throws Exception {
        Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "checkValidations", "checkValidations");
        return (retObjArr);
    }

    @DataProvider(name = "SAJobWithOutFileNames")
    public static Object[][] SAJobWithOutFileNames(ITestContext context) throws Exception {
        System.out.println("init SAJobWithOutFileNames");
        return DataProviderUtility.appendOutFileNameToEachRow(SAJobs(context), SAOutFileNames(context));
    }

    @BeforeClass(groups = {"prerequisite"})
    public void testJobsBySANavigate() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
        //emailUtils.markAllEmailsAsUnread(EmailUtils.EmailFolder.JOBSUCCESS);
    }

    @Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUp.class, groups = {"prerequisite"})
    public void testJobsBySALogin(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
    }

    @Test(dataProvider = "SAIdAndInputJobDetails", groups = {"regressionSuite"})
    public void testSAUploadFileConfigureAndStartGeocoding(String userID, String firstName, String secondName, String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                           String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                           String matchMode, String totalRecords) throws Exception {
//        System.out.println("inputfile: " + inputFileName);
//        a.navigateToUploadFile();
//        saOutFileNamesArray[x][0] = a.uploadFileConfigureAndStartJob(secondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
//                outputFields, outputFormat, coordSystem, country, matchMode, totalRecords);
//        System.out.println("SAOutFileNamesArray: " + saOutFileNamesArray[x][0] + "\n");
//        x++;
        saOutFileNamesArray[0][0] = "UnevenInvrtdComa_FG_FreeUS100820135236775";
        saOutFileNamesArray[1][0] = "JobSuccessNoGeocode_FG_FreeUS100820135236992";
    }

    //@Test(dataProvider = "checkValidations", groups = {"regressionSuite"})
    public void testUploadIncorrectFilesAndCheckValidations(String inputFileName, String geocodingType, String expectedMessage) throws Exception {
        a.navigateToDashboard();
        a.navigateToUploadFile();
        a.uploadIncorrectFilesAndCheckValidations(inputFileName, geocodingType, expectedMessage);
    }

    @Test(dataProvider = "SAOutFileNames", groups = {"regressionSuite"})
    public void testVerifySAJobEmailsAndAccessDetailsDirectly(String outFileName) throws Exception {
        Boolean outFileFound = false;
        System.out.println("**outFileName: **" + outFileName + "\n");
        int p = outFileName.indexOf("Free");
        int q = outFileName.length();
        String userID = "mmoAutomated+" + outFileName.substring(p, q-3) + "@gmail.com";
        if(abc == 1) {
            Emails = emailUtils.getMessagesBySubjectInFolder("MapMarker File Ready", true,
                    2, EmailUtils.EmailFolder.JOBSUCCESS);
            abc++;
            Assert.assertTrue(Emails.length == 2, "Expected unread messages:2, actual: " + Emails.length);
        }
        List<Message> list = new ArrayList<>(Arrays.asList(Emails));
        for (Message email : Emails) {
            System.out.println("**EMAIL ID: **" + email.getAllRecipients()[0].toString() + "\n");
            System.out.println("**user ID: **" + userID + "\n");
            if(emailUtils.isTextInMessage(email, outFileName.substring(0, 15) + "=" + outFileName.substring(15)))
            {
                Assert.assertEquals(email.getAllRecipients()[0].toString(), userID,
                        "Recipient incorrect; expected: " + userID + " but actual: " + email.getAllRecipients()[0].toString());
                outFileFound = true;
                String jobTokenFromEmail = emailUtils.getJobTokenFromEmail(email);
                driver.get("https://mandrillapp.com/track/click/30875726/mapmarker-qa.li.precisely.services?p=" + jobTokenFromEmail);
                ip.isURLContains(driver, "geocoderesult?jobId=");
                ip.isElementClickableByXpath(driver, "//h1", 60);
                ip.isTextPresentByXPATH(driver ,"//h1", outFileName);
                list.remove(email);
                Emails = list.toArray(new Message[0]);
                System.out.println("Inside Email LENGTH: " + Emails.length);
                break;
            }
        }
        if(outFileFound == false){
            u.illegalStateException("Job completion email not found for: " + outFileName);
        }
    }

    @AfterClass(groups = {"prerequisite"})
    public void testLogout() throws Exception {
        a.logOut();
    }
}
