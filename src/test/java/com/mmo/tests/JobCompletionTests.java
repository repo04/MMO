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
                Job.SAOutFileNames(context), JobBySubUsers.Admin1OutFileNames(context), JobBySubUsers.Admin2OutFileNames(context),
                JobBySubUsers.User1OutFileNames(context), JobBySubUsers.User2OutFileNames(context));
    }

    @DataProvider(name = "Admin1AndJobDetails")
    public static Iterator<Object[]> Admin1AndJobDetails(ITestContext context) throws Exception {
        System.out.println("init Admin1AndJobDetails");
        return DataProviderUtility.cartesianProviderFrom(
                CreateSubAccountTests.SubscriptionAdminCreateAdminDetails(context), AllJobDetails(context));
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

    @Test(dataProvider = "Admin1AndJobDetails", groups = {"regressionSuite"})
    public void testAdmin1CheckWhichAllJobsAreShown(String userID, String userFirstName, String userSecondName,
            String inputFileName, String geocodingType, String autoDrag, String dragColumns,
            String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
            String matchMode, String outFileName) throws Exception {
        if(x == 1){
            a.login(userID);
            System.out.print("****Logged in with user:" + userID + "\n");
        }
        System.out.print("****x value:" + x + "\n");
        a.verifyJobsShownToUser(outFileName);
        x++;
        if(x == 7){
            a.logOut();
            System.out.print("****logged out with user:" + userID + "\n");
            x = 1;
        }
    }
}
