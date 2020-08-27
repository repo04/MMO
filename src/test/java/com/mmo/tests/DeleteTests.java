package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import org.testng.Reporter;
import org.testng.annotations.*;

public class DeleteTests extends BaseClass {
    private Actions a = new Actions();

    @BeforeClass(groups = {"prerequisite"})
    public void testDeleteNavigateToLogin() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "AdminCreateAdminDetails", dataProviderClass = CreateSubAccountTests.class , groups = {"regressionSuite"})
    public void testDeleteLogin(String adminID, String admin1FirstName, String admin1SecondName) throws Exception {
        a.login(adminID);
    }

    @Test(dataProvider = "SubscriptionAdminCreateAdminDetails", dataProviderClass = CreateSubAccountTests.class, groups = "regressionSuite")
    public void testAdmin2DeleteAdmin1(String adminID, String admin2FirstName, String admin2SecondName) throws Exception {
        a.navigateToUsers();
        a.deleteUser(adminID);
        Reporter.log("Admin deleted: " + adminID, true);
    }

    @Test(dataProvider = "AdminCreateUserDetails", dataProviderClass = CreateSubAccountTests.class, groups = "regressionSuite")
    public void testAdmin2DeleteUser2(String userID, String admin1FirstName, String admin1SecondName) throws Exception {
        a.navigateToUsers();
        a.deleteUser(userID);
        Reporter.log("User deleted: " + userID, true);
    }

    @Test(dataProvider = "User1OutFileNames", dataProviderClass = JobExecutionBySubAccountTests.class, groups = "regressionSuite")
    public void testAdmin2DeleteUser1Job(String outFileName) throws Exception {
        a.navigateToDashboard();
        a.verifyJobsShownToUser("Admin", outFileName);
        a.deleteJob("Admin", outFileName);
        Reporter.log("Output Job deleted: " + outFileName, true);
    }

    @AfterClass(groups = {"prerequisite"})
    public void testDeleteAdminLogout() throws Exception {
        a.logOut();
    }
}