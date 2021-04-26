package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import com.mmo.util.EmailUtils;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Iterator;

public class DegradeTests extends BaseClass {
    private Actions a = new Actions();
    private String[] textInMessage = new String[2];
    static String[][] executeJobDegrade5ktoFree = new String[1][1];

    @DataProvider(name = "Deg5ktoFree")
    public static Object[][] Deg5ktoFree(ITestContext context) throws Exception {
        Object[][] retObjArr = u.getTableArray(directory.getCanonicalPath() + File.separator + "test" + File.separator + "resources" + File.separator + "New.xls",
                "executeJobs", "Deg5ktoFree");
        return retObjArr;
    }

    @DataProvider(name = "Paid5kLoginAndDeg5ktoFreeJobDetails")
    public static Iterator<Object[]> Paid5kLoginAndDeg5ktoFreeJobDetails(ITestContext context) throws Exception {
        System.out.println("init Paid5kLoginAndDeg5ktoFreeJobDetails");
        return DataProviderUtility.cartesianProviderFrom(
                SignUpTests.Paid5kUserDetails(context), Deg5ktoFree(context));
    }

    @BeforeClass(groups = {"prerequisite"})
    public void testDegradeTestsNavigate() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "Paid5kUserDetails", dataProviderClass = SignUpTests.class, groups = {"regressionSuite"})
    public void testDegrade5kToFreePlanAndCheckEmail(String userID, String userFirstName, String userSecondName) throws Exception {
        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.PLANCHANGE);
        a.login(userID);
        a.navigateToDashboard();
        a.navigateToBillingPlan();
        a.changePlan(userID, userFirstName, userSecondName, "Free");
        Reporter.log("5k user degraded to Free <br/>", true);
        textInMessage[0] = "Your MapMarker plan has been updated.";
        textInMessage[1] = "Thank you for= updating your MapMarker plan. " +
                "Please visit the below link to view your Map=Marker Dashboard.";
        emailUtils.waitForEmailReceived("Your MapMarker subscription has been updated", EmailUtils.EmailFolder.PLANCHANGE, 1);
        emailUtils.isTextPresentInMessage("Your MapMarker subscription has been updated", userID, textInMessage, EmailUtils.EmailFolder.PLANCHANGE);
        Reporter.log("5k user degraded to Free - Email successfully received <br/>", true);
    }

    @Test(dataProvider = "Paid5kLoginAndDeg5ktoFreeJobDetails", groups = {"regressionSuite"})
    public void testExecuteJobAfterDegrade(String userID, String userFirstName, String userSecondName,
                                           String inputFileName, String geocodingType, String autoDrag, String dragColumns,String dropFieldsToGeocode,
                                           String outputFields, String outputFormat, String coordSystem, String country, String matchMode, String totalRecords,
                                           String advanceGeocoding, String multiMatch) throws Exception {
        a.navigateToDashboard();
        a.navigateToUploadFile();
        executeJobDegrade5ktoFree[0][0] = a.uploadFileConfigureAndStartJob(userSecondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
                outputFields, outputFormat, coordSystem, country, matchMode, totalRecords, advanceGeocoding, multiMatch);
        Reporter.log("JobAfterDegrade: " + executeJobDegrade5ktoFree[0][0] + "<br/>", true);
        a.waitforJobToGetComplete(userSecondName, executeJobDegrade5ktoFree[0][0], 30000);
        a.logOut();
    }
}
