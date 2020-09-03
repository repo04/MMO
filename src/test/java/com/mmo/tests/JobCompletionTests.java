package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import com.mmo.util.EmailUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import javax.mail.Message;
import java.util.Iterator;

public class JobCompletionTests extends BaseClass {
    private Actions a = new Actions();
    int x = 1;
    private int pqr = 1;
    Message[] Emails = null;

    @DataProvider(name = "AllJobDetails")
    public static Object[][] AllJobDetails(ITestContext context) throws Exception {
        System.out.println("init AllJobDetails");
        return DataProviderUtility.append2DJobDetailsArrayVertically(
                JobExecutionBySubscriptionAdminTests.SAJobWithOutFileNames(context), JobExecutionBySubAccountTests.Admin1JobWithOutFileNames(context),
                JobExecutionBySubAccountTests.Admin2JobWithOutFileNames(context),JobExecutionBySubAccountTests.User1JobWithOutFileNames(context),
                JobExecutionBySubAccountTests.User2JobWithOutFileNames(context));
    }

    @DataProvider(name = "FreeUSSAAndJobDetails")
    public static Iterator<Object[]> FreeUSSAAndJobDetails(ITestContext context) throws Exception {
        System.out.println("init FreeUSSAAndJobDetails");
        return DataProviderUtility.cartesianProviderFrom(
                SignUpTests.FreeUSUserDetails(context), AllJobDetails(context));
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

    @DataProvider(name = "SubUsersOutFileNames")
    public static Object[][] SubUsersOutFileNames(ITestContext context) throws Exception {
        System.out.println("init SubUsersOutFileNames");
        return DataProviderUtility.append2DJobDetailsArrayVertically(
                JobExecutionBySubAccountTests.Admin1OutFileNames(context), JobExecutionBySubAccountTests.Admin2OutFileNames(context),
                JobExecutionBySubAccountTests.User1OutFileNames(context), JobExecutionBySubAccountTests.User2OutFileNames(context));
    }

    @BeforeClass(groups = { "prerequisite" })
    public void testJobCompletionLogIn(ITestContext context) throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "AllAdminsAndJobDetails", groups = {"regressionSuite"})
    public void testAdminsVerifyJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength(String userID, String userFirstName, String userSecondName,
                                                                String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                                String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                                String matchMode, String totalRecords, String outFileName) throws Exception {

        System.out.println("inputfile: " + inputFileName);
        System.out.println("outFileName: " + outFileName);
        if(x == 1){
            a.login(userID);
        }
        a.verifyJobsShownToUser(userSecondName, outFileName);
        if(outFileName.contains(userSecondName)){
            System.out.print("****Admin Out****:" + outFileName + "\n");
            a.waitforJobToGetComplete(userSecondName, outFileName);
            a.downloadOutputVerifyExtensionsAndDataTypeLength(userSecondName, outFileName, outputFormat);
            a.verifyJobDetails(userSecondName, inputFileName, geocodingType, outputFields, outputFormat,
                    coordSystem, country, matchMode, totalRecords, outFileName);
            a.navigateToDashboard();
        }
        x++;
        if(x == 7){
            a.logOut();
            x = 1;
        }
   }

    @Test(dataProvider = "AllUsersAndJobDetails", groups = {"regressionSuite"})
    public void testUsersJobsJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength(String userID, String userFirstName, String userSecondName,
                                                               String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                               String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                               String matchMode, String totalRecords, String outFileName) throws Exception {
        System.out.println("inputfile: " + inputFileName);
        System.out.println("outFileName: " + outFileName);
        if(x == 1){
            a.login(userID);
        }
        a.verifyJobsShownToUser(userSecondName, outFileName);
        if(outFileName.contains(userSecondName)){
            System.out.print("****User Out****:" + outFileName + "\n");
            a.waitforJobToGetComplete(userSecondName, outFileName);
            a.verifyJobDetails(userSecondName, inputFileName, geocodingType, outputFields, outputFormat, coordSystem, country, matchMode, totalRecords, outFileName);
            a.downloadOutputVerifyExtensionsAndDataTypeLength(userSecondName, outFileName, outputFormat);
            a.navigateToDashboard();
        }
        x++;
        if(x == 7){
            a.logOut();
            x = 1;
        }
    }

    @Test(dataProvider = "FreeUSSAAndJobDetails", groups = {"regressionSuite"})
    public void testSubscriptionAdminVerifyAllJobsAreVisible(String userID, String userFirstName, String userSecondName,
                                                             String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                             String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                             String matchMode, String totalRecords, String outFileName) throws Exception {
        System.out.println("inputfile: " + inputFileName);
        System.out.println("outFileName: " + outFileName);
        if(x == 1){
            a.login(userID);
        }
        a.verifyJobsShownToUser(userSecondName, outFileName);
        x++;
        if(x == 7){
            a.logOut();
            x = 1;
        }
   }

    @Test(dataProvider = "SubUsersOutFileNames", groups = {"regressionSuite"})
    public void testSubUsersVerifyJobCompleteEmail(String outFileName) throws Exception {
        System.out.println("**outFileName: **" + outFileName + "\n");
        int p = outFileName.indexOf("Free");
        int q = outFileName.length();
        String userID = "mmoAutomated+" + outFileName.substring(p, q-3) + "@gmail.com";
        if(pqr == 1) {
            Emails = emailUtils.getMessagesBySubjectInFolder("MapMarker File Ready", true,
                    4, EmailUtils.EmailFolder.JOBSUCCESS);
            pqr++;
            Assert.assertTrue(Emails.length == 4, "Expected unread messages:4, actual: " + Emails.length);
        }
        if(!emailUtils.testVerifyJobCompleteEmailAndAccessDetailsDirectly(outFileName, userID, false, "N")){
            u.illegalStateException("Job completion email not found for: " + outFileName);
        }
    }
}