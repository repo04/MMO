package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import com.mmo.util.EmailUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JobCompletionTests extends BaseClass {
    private Actions a = new Actions();
    int x = 1;
    int abc = 1;
    Message[] Emails = null;

    @DataProvider(name = "AllJobDetails")
    public static Object[][] AllJobDetails(ITestContext context) throws Exception {
        System.out.println("init AllJobDetails");
        return DataProviderUtility.append2DJobDetailsArrayVertically(
                JobsBySATests.SAJobWithOutFileNames(context), JobExecutionTests.Admin1JobWithOutFileNames(context), JobExecutionTests.Admin2JobWithOutFileNames(context),
                JobExecutionTests.User1JobWithOutFileNames(context), JobExecutionTests.User2JobWithOutFileNames(context));
    }

    @DataProvider(name = "FreeUSSAAndJobDetails")
    public static Iterator<Object[]> FreeUSSAAndJobDetails(ITestContext context) throws Exception {
        System.out.println("init FreeUSSAAndJobDetails");
        return DataProviderUtility.cartesianProviderFrom(
                SignUp.FreeUSUserDetails(context), AllJobDetails(context));
    }

    @DataProvider(name = "AllAdminsAndJobDetails")
    public static Iterator<Object[]> AllAdminsAndJobDetails(ITestContext context) throws Exception {
        System.out.println("init AllAdminsAndJobDetails");
        return DataProviderUtility.cartesianProviderFrom(
                CreateSubAccountTests.AllAdminDetails(context), AllJobDetails(context));
    }

    @DataProvider(name = "AllUsersAndJobDetails")
    public static Iterator<Object[]> AllUsersAndJobDetails(ITestContext context) throws Exception {
        System.out.println("init AllUsersAndJobDetails");
        return DataProviderUtility.cartesianProviderFrom(
                CreateSubAccountTests.AllUserDetails(context), AllJobDetails(context));
    }

    @BeforeClass(groups = { "prerequisite" })
    public void testJobCompletionLogIn(ITestContext context) throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    //@Test(dataProvider = "defaultJobDetails", groups = {"regressionSuite"})
    public void testWaitForJobToGetCompleteDownloadAndCompare(String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                              String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                              String matchMode, String outFileName) throws Exception {
        a.downloadOutputFileAndCompare(outFileName, outputFormat);
    }

    @Test(dataProvider = "AllAdminsAndJobDetails", groups = {"regressionSuite"})
    public void testAdminsJobsVisibleCompletionAndVerifyDetails(String userID, String userFirstName, String userSecondName,
                                                                     String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                                     String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                                     String matchMode, String outFileName) throws Exception {

        if(x == 1){
            a.login(userID);
        }
        a.verifyJobsShownToUser(userSecondName, outFileName);
        if(outFileName.contains(userSecondName)){
            System.out.print("****Admin Out****:" + outFileName + "\n");
            a.waitforJobToGetComplete(userSecondName, outFileName);
            a.verifyJobDetails(userSecondName, inputFileName, geocodingType, outputFields, outputFormat,
                    coordSystem, country, matchMode, outFileName);
            a.navigateToDashboard();
        }
        x++;
        if(x == 7){
            a.logOut();
            x = 1;
        }
    }

    @Test(dataProvider = "AllUsersAndJobDetails", groups = {"regressionSuite"})
    public void testUsersJobsVisibleCompletionAndVerifyDetails(String userID, String userFirstName, String userSecondName,
                                                                    String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                                    String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                                    String matchMode, String outFileName) throws Exception {
        if(x == 1){
            a.login(userID);
        }
        a.verifyJobsShownToUser(userSecondName, outFileName);
        if(outFileName.contains(userSecondName)){
            System.out.print("****User Out****:" + outFileName + "\n");
            a.waitforJobToGetComplete(userSecondName, outFileName);
            a.verifyJobDetails(userSecondName, inputFileName, geocodingType, outputFields, outputFormat, coordSystem, country, matchMode, outFileName);
            a.navigateToDashboard();
        }
        x++;
        if(x == 7){
            a.logOut();
            x = 1;
        }
    }

    @Test(dataProvider = "FreeUSSAAndJobDetails", groups = {"regressionSuite"})
    public void testSubscriptionAdminJobsVisibleCompletionAndVerifyDetails(String userID, String userFirstName, String userSecondName,
                                                                        String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                                        String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                                        String matchMode, String outFileName) throws Exception {
        if(x == 1){
            a.login(userID);
        }
        a.verifyJobsShownToUser(userSecondName, outFileName);
        if(outFileName.contains(userSecondName)){
            System.out.print("****SA Out****:" + outFileName + "\n");
            a.waitforJobToGetComplete(userSecondName, outFileName);
            a.verifyJobDetails(userSecondName, inputFileName, geocodingType, outputFields, outputFormat,
                    coordSystem, country, matchMode, outFileName);
            a.navigateToDashboard();
        }
        x++;
        if(x == 7){
            a.logOut();
            x = 1;
        }
    }

    @Test(dataProvider = "OutFileNames", dataProviderClass = JobExecutionTests.class, groups = {"regressionSuite"})
    public void testVerifyAllJobEmails(String outFileName) throws Exception {
        Boolean outFileFound = false;
        System.out.println("**outFileName: **" + outFileName + "\n");
        int p = outFileName.indexOf("Free");
        int q = outFileName.length();
        String userID = "mmoAutomated+" + outFileName.substring(p, q-3) + "@gmail.com";
        if(abc == 1) {
            Emails = emailUtils.getMessagesBySubjectInFolder("MapMarker File Ready", true,
                    6, EmailUtils.EmailFolder.JOBSUCCESS);
            abc++;
            Assert.assertTrue(Emails.length == 6, "Expected unread messages:6, actual: " + Emails.length);
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
}