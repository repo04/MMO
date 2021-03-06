package com.mmo.tests;

import com.mmo.util.*;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Iterator;

public class JobExecutionBySubscriptionAdminTests extends BaseClass {
    private Actions a = new Actions();
    private SortCSV sort = new SortCSV();
    private CompareFiles cmpr = new CompareFiles();
    static String[][] saJobWithOutFileNamesArray;
    static String[][] saOutFileNamesArray;
    Boolean check=true;
    private  int abc = 1;
    int m = 0;
    int e = 0;
    String loginID;
    private String[] textInMessage;

    @DataProvider(name = "SAJobs")
    public static Object[][] SAJobs(ITestContext context) throws Exception {
        Object[][] retObjArr = u.getTableArray(directory.getCanonicalPath() + File.separator + "src" + File.separator + "test" +
                        File.separator + "resources" + File.separator + "New.xls","executeJobs", "SAJobs");
        return retObjArr;
    }

    @DataProvider(name = "SAIdAndInputJobDetails")
    public static Iterator<Object[]> SAIdAndInputJobDetails(ITestContext context) throws Exception {
        System.out.println("init SAIdAndInputJobDetails");
        return DataProviderUtility.cartesianProviderFrom(SignUpTests.FreeUSUserDetails(context), SAJobs(context));
    }

    @DataProvider(name = "SAOutFileNames")
    public static Object[][] SAOutFileNames(ITestContext context) throws Exception {
        System.out.println("init SAOutFileNames");
        return saOutFileNamesArray;
    }

    @DataProvider(name = "SAJobWithOutFileNames")
    public static Object[][] SAJobWithOutFileNames(ITestContext context) throws Exception {
        System.out.println("init SAJobWithOutFileNames");
        return saJobWithOutFileNamesArray;
    }

    @DataProvider(name = "FreeUSSAAndJobDetails")
    public static Iterator<Object[]> FreeUSSAAndJobDetails(ITestContext context) throws Exception {
        System.out.println("init FreeUSSAAndJobDetails");
        return DataProviderUtility.cartesianProviderFrom(
                SignUpTests.FreeUSUserDetails(context), SAJobWithOutFileNames(context));
    }

    @DataProvider(name = "checkValidations")
    public static Object[][] checkValidations(ITestContext context) throws Exception {
        Object[][] retObjArr = u.getTableArray(directory.getCanonicalPath() + File.separator + "src" + File.separator + "test" +
                        File.separator + "resources" + File.separator + "New.xls",
                "checkValidations", "checkValidations");
        return (retObjArr);
    }

    @BeforeClass(groups = {"prerequisite"})
    public void testJobsBySANavigate() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUpTests.class, groups = {"prerequisite"})
    public void testJobsBySALogin(String userID, String userFirstName, String userSecondName) throws Exception {
        loginID = userID;
        a.login(userID);
//        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.JOBFAIL);
//        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.JOBSUCCESS);
    }

    @Test(dataProvider = "SAIdAndInputJobDetails", groups = {"regressionSuite"})
    public void testSubscriptionAdminUploadFileConfigureAndStartGeocoding(String userID, String firstName, String secondName, String inputFileName, String geocodingType, String autoDrag, String                                                                dragColumns,String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                                          String matchMode, String totalRecords, String advanceGeocoding, String multiMatch, String inpRows) throws Exception {
        System.out.println("inputfile: " + inputFileName);
        if (m == 0){
            saOutFileNamesArray = new String[Integer.valueOf(inpRows)][1];
            totalJobs = Integer.valueOf(inpRows);
        }
        a.navigateToUploadFile();
        System.out.println("m value: " + m);
        saOutFileNamesArray[m][0] = a.uploadFileConfigureAndStartJob(secondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
                outputFields, outputFormat, coordSystem, country, matchMode, totalRecords, advanceGeocoding, multiMatch);
//      saOutFileNamesArray[m][0] = "UnevenInvrtdComa_RG_FreeUS270121155055741";
        Reporter.log("outFileBySA: " + saOutFileNamesArray[m][0] + "<br/>", true);
        appendOutFileName(inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
                outputFields, outputFormat, coordSystem, country, matchMode, totalRecords, advanceGeocoding, multiMatch, inpRows, saOutFileNamesArray[m][0]);
        Thread.sleep(45000);
//      saOutFileNamesArray[0][0] = "InpTesting5_2_FreeUS270121155055621";
//      saOutFileNamesArray[1][0] = "JobSuccessNoGeocode_FG_FreeUS100820135236992";
    }

    @Test(dataProvider = "checkValidations", groups = {"regressionSuite"})
    public void testUploadIncorrectFilesAndCheckValidations(String inputFileName, String geocodingType, String expectedMessage) throws Exception {
        a.navigateToDashboard();
        a.navigateToUploadFile();
        a.uploadIncorrectFilesAndCheckValidations(loginID, inputFileName, geocodingType, expectedMessage);
    }

    @Test(dataProvider = "FreeUSSAAndJobDetails", groups = {"regressionSuite"})
    public void testSubscriptionAdminVerifyJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength(String userID, String userFirstName, String userSecondName,
                                                                           String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                                           String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                                           String matchMode, String totalRecords, String advanceGeocoding, String multiMatch, String outFileName) throws Exception {
        System.out.println("inputfile: " + inputFileName);
        System.out.println("outFileName: " + outFileName);
        a.navigateToDashboard();
        a.verifyJobsShownToUser(userSecondName, outFileName);
        if(outFileName.contains(userSecondName)){
            System.out.print("****SA Out****:" + outFileName + "\n");
            a.waitforJobToGetComplete(userSecondName, outFileName, 30000);
            a.downloadOutputVerifyExtensionsAndDataTypeLength(userSecondName, outFileName, outputFormat);
            if(outputFormat.equalsIgnoreCase("CSV") && inputFileName.startsWith("FGCmpr")){
                sort.sortCSVFile(outFileName);
                cmpr.compareCSVFiles(outFileName, advanceGeocoding, multiMatch);
            }
            a.verifyJobDetails(userSecondName, inputFileName, geocodingType, outputFields, outputFormat, coordSystem, country, matchMode, totalRecords, advanceGeocoding, multiMatch, outFileName);
            Reporter.log("Subscription Admin verified job details for output file: " + outFileName + "<br/>", true);
            a.navigateToDashboard();
        }
    }

    @AfterClass(groups = {"prerequisite"})
    public void testLogout() throws Exception {
        a.logOut();
    }

    private void appendOutFileName(String... params) throws Exception {

        for (String param : params){
            if (m == 0 && e == 13){
                saJobWithOutFileNamesArray = new String[Integer.valueOf(param)][14];
            }
            e++;
        }

        int j = 0;
        for (String param : params){
            //System.out.println("param: " + param +"\n");
            if(j < 13){
                saJobWithOutFileNamesArray[m][j] = param;
                //System.out.println("["+m+"]["+j+"]: " + saJobWithOutFileNamesArray[m][j] +"\n");
            }else if(j > 13){
                int q = j-1;
                saJobWithOutFileNamesArray[m][q] = param;
                //System.out.println("["+m+"]["+q+"] " + saJobWithOutFileNamesArray[m][q] +"\n");
            }
            j++;
        }
        m++;
    }

    /*@Test(groups = {"regressionSuite"})
    public void testSubscriptionAdminVerifyJobsFailureEmails() throws Exception {
        emailUtils.waitForEmailReceived("MapMarker Job Complete", EmailUtils.EmailFolder.JOBFAIL, failJobNames.size());
        Emails = emailUtils.getMessagesBySubjectInFolder("MapMarker Job Complete", true, failJobNames.size(), EmailUtils.EmailFolder.JOBFAIL);
        System.out.println("failJobCount: " + failJobNames.size());
        for(String jobName: failJobNames){
            System.out.println("Fail Job Name: " + jobName);
            if(!emailUtils.testVerifyJobCompleteEmailAndAccessDetailsDirectly(jobName, loginID, false, "Y")){
                u.illegalStateException("Job failure email not found for: " + jobName);
            }
        }
        Reporter.log("Subscription Admin Verified Job Failure Emails(Quota for Frwd/Rev) <br/>", true);
    }*/

    /*@Test(dataProvider = "SAOutFileNames", groups = {"regressionSuite"})
    public void testSubscriptionAdminVerifyJobCompleteEmailAndAccessDetailsDirectly(String outFileName) throws Exception {
        System.out.println("**outFileName: **" + outFileName + "\n");
        System.out.println("**final M value: **" + m + "\n");
        int p = outFileName.indexOf("Free");
        int q = outFileName.length();
        String userID = "mmoAutomated+" + outFileName.substring(p, q-3) + "@gmail.com";
        if(abc == 1) {
            emailUtils.waitForEmailReceived("MapMarker File Ready", EmailUtils.EmailFolder.JOBSUCCESS, m);
            Emails = emailUtils.getMessagesBySubjectInFolder("MapMarker File Ready", true, m, EmailUtils.EmailFolder.JOBSUCCESS);
            abc++;
            Assert.assertTrue(Emails.length == m, "Expected unread messages:" + m + ", actual: " + Emails.length);
        }
        if(!emailUtils.testVerifyJobCompleteEmailAndAccessDetailsDirectly(outFileName, userID, false, "N")){
            u.illegalStateException("Job completion email not found for: " + outFileName);
        }
    }*/
}