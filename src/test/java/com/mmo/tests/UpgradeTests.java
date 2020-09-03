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

import java.util.Iterator;

public class UpgradeTests extends BaseClass {

    private Actions a = new Actions();
    String[] textInMessage = new String[2];
    static String[][] executeJobUpgradeFreeto5k = new String[1][1];

    @DataProvider(name = "UpgFreeto5k")
    public static Object[][] UpgFreeto5k(ITestContext context) throws Exception {
        Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "executeJobs", "UpgFreeto5k");
        return retObjArr;
    }

    @DataProvider(name = "FreeUSLoginAndUpgFreeto5kJobDetails")
    public static Iterator<Object[]> FreeUSLoginAndUpgFreeto5kJobDetails(ITestContext context) throws Exception {
        System.out.println("init FreeUSLoginAndUpgFreeto5kJobDetails");
        return DataProviderUtility.cartesianProviderFrom(
                SignUpTests.FreeUSUserDetails(context), UpgFreeto5k(context));
    }

    @BeforeClass(groups = {"prerequisite"})
    public void testUpgradeTestsNavigate() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUpTests.class, groups = {"regressionSuite"})
    public void testUpgradeFreeTo5kPlanAndCheckEmail(String userID, String userFirstName, String userSecondName) throws Exception {
        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.PLANCHANGE);
        a.login(userID);
        a.navigateToBillingPlan();
        a.changePlan(userID, userFirstName, userSecondName, "5k");
        Reporter.log("Free user upgraded to 5k", true);
        textInMessage[0] = "Your MapMarker plan has been updated.";
        textInMessage[1] = "Thank you for= updating your MapMarker plan. " +
                "Please visit the below link to view your Map=Marker Dashboard.";
        emailUtils.isTextPresentInMessage("Your MapMarker subscription has been updated",userID, textInMessage, EmailUtils.EmailFolder.PLANCHANGE);
    }

    @Test(dataProvider = "FreeUSLoginAndUpgFreeto5kJobDetails", groups = {"regressionSuite"})
    public void testExecuteJobAfterUpgrade(String userID, String userFirstName, String userSecondName,
                                           String inputFileName, String geocodingType, String autoDrag, String dragColumns,String dropFieldsToGeocode,
                                           String outputFields, String outputFormat, String coordSystem, String country, String matchMode, String totalRecords) throws Exception {
        a.navigateToDashboard();
        a.navigateToUploadFile();
        executeJobUpgradeFreeto5k[0][0] = a.uploadFileConfigureAndStartJob(userSecondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
                outputFields, outputFormat, coordSystem, country, matchMode, totalRecords);
        Reporter.log("JobAfterUpgradeto5k: " + executeJobUpgradeFreeto5k[0][0], true);
        a.waitforJobToGetComplete("FreeUS260720161144", executeJobUpgradeFreeto5k[0][0]);
    }

    @Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUpTests.class, groups = {"regressionSuite"})
    public void testUpgrade5kToProfPlanAndCheckEmail(String userID, String userFirstName, String userSecondName) throws Exception {
        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.PLANCHANGE);
        a.navigateToDashboard();
        a.navigateToBillingPlan();
        a.changePlan(userID, userFirstName, userSecondName, "Prof");
        Reporter.log("5k user upgraded to Prof", true);
        a.logOut();
        textInMessage[0] = "Your MapMarker plan has been updated.";
        textInMessage[1] = "Thank you for= updating your MapMarker plan. " +
                "Please visit the below link to view your Map=Marker Dashboard.";
       emailUtils.isTextPresentInMessage("Your MapMarker subscription has been updated", userID, textInMessage, EmailUtils.EmailFolder.PLANCHANGE);
    }

    //@Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUpTests.class, groups = {"regressionSuite"})
    public void testUpgradeFreeToProfPlan(String userID, String userFirstName, String userSecondName) throws Exception {
        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.PLANCHANGE);
        a.login(userID);
        a.navigateToBillingPlan();
        a.changePlan(userID, userFirstName, userSecondName, "Prof");
        a.logOut();
        textInMessage[0] = "Your MapMarker plan has been updated.";
        textInMessage[1] = "Thank you for= updating your MapMarker plan. " +
                "Please visit the below link to view your Map=Marker Dashboard.";
       emailUtils.isTextPresentInMessage("Your MapMarker subscription has been updated", userID, textInMessage, EmailUtils.EmailFolder.PLANCHANGE);
    }
}
