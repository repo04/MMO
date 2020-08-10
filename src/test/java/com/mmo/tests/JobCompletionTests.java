package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.util.Iterator;

public class JobCompletionTests extends BaseClass {
    private Actions a = new Actions();
    int x = 1;

    @DataProvider(name = "AllJobDetails")
    public static Object[][] AllJobDetails(ITestContext context) throws Exception {
        System.out.println("init AllJobDetails");
        return DataProviderUtility.append2DJobDetailsArrayVertically(
                JobExecutionTests.SAOutFileNames(context), JobExecutionTests.Admin1OutFileNames(context), JobExecutionTests.Admin2OutFileNames(context),
                JobExecutionTests.User1OutFileNames(context), JobExecutionTests.User2OutFileNames(context));
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
    public void testLogIn(ITestContext context) throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    //@Test(dataProvider = "defaultJobDetails", groups = {"regressionSuite"})
    public void testWaitForJobToGetCompleteDownloadAndCompare(String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                              String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                              String matchMode, String outFileName) throws Exception {
        a.navigateToDashboard();
        System.out.println("inputFileName: " + inputFileName + "\n");
        System.out.println("geocodingType: " + geocodingType + "\n");
        System.out.println("autoDrag: " + autoDrag + "\n");
        System.out.println("dragColumns: " + dragColumns + "\n");
        System.out.println("dropFieldsToGeocode: " + dropFieldsToGeocode + "\n");
        System.out.println("outputFields: " + outputFields + "\n");
        System.out.println("outputFormat: " + outputFormat + "\n");
        System.out.println("coordSystem: " + coordSystem + "\n");
        System.out.println("country: " + country + "\n");
        System.out.println("matchMode: " + matchMode + "\n");
        System.out.println("outFileName: " + outFileName + "\n");
		/*for (int i = 0; i < outFileNamesList.size(); i++) {
			a.waitforJobToGetComplete(outFileNamesList.get(i));
			System.out.print("****Download File Start****:" + "\n");
			a.downloadOutputFileAndCompare(outFileNamesList.get(i), outFileFormatList.get(i));
		}
		a.waitforJobToGetComplete(outFileName);
		System.out.print("****Download File Start****:" + "\n");
		a.downloadOutputFileAndCompare(outFileName, outputFormat);*/
    }

    //@Test(dataProvider = "AllAdminsAndJobDetails", groups = {"regressionSuite"})
    public void testAdminsVerifyJobsVisibleCompletionDetailsAndEmail(String userID, String userFirstName, String userSecondName,
            String inputFileName, String geocodingType, String autoDrag, String dragColumns,
            String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
            String matchMode, String outFileName) throws Exception {
        if(x == 1){
            a.login(userID);
        }
        a.verifyJobsShownToUser(userSecondName, outFileName);
        if(outFileName.contains(userSecondName)){
            a.waitforJobToGetComplete(userSecondName, outFileName);
        }
        x++;
        if(x == 7){
            a.logOut();
            x = 1;
        }
    }

    @Test(dataProvider = "AllUsersAndJobDetails", groups = {"regressionSuite"})
    public void testUsersVerifyJobsVisibleCompletionDetailsAndEmail(String userID, String userFirstName, String userSecondName,
                                                   String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                   String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                   String matchMode, String outFileName) throws Exception {
        if(x == 1){
            a.login(userID);
        }
        a.verifyJobsShownToUser(userSecondName, outFileName);
        if(outFileName.contains(userSecondName)){
            a.waitforJobToGetComplete(userSecondName, outFileName);
            a.verifyJobDetails(userSecondName, inputFileName, geocodingType, outputFields, outputFormat,
                    coordSystem, country, matchMode, outFileName);
        }
        x++;
        if(x == 7){
            a.logOut();
            x = 1;
        }
    }

    //@Test(dataProvider = "FreeUSSAAndJobDetails", groups = {"regressionSuite"})
    public void testSubscriptionAdminVerifyJobCompletionDetailsAndEmail(String userID, String userFirstName, String userSecondName,
                                                        String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                        String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                        String matchMode, String outFileName) throws Exception {
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
}
