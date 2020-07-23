package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SubUsersVerifyDetailTests extends BaseClass {

    Actions a = new Actions();

    @BeforeClass(groups = {"prerequisite"})
    public void testSubUsersVerifyDetailsNavigate() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "AllAdminDetails", dataProviderClass = CreateSubAccountTests.class, groups = {"regressionSuite"})
    public void testAdminUserVerifyDetails(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.verifyDashboard(userFirstName, userSecondName);
        a.navigateToProfilePage();
        a.verifyProfilePage(userID, userFirstName, userSecondName);
        a.navigateToUsers();
        a.verifyUserDetailInUsersList(userID, userFirstName, userSecondName, "Admin");
        a.logOut();
    }

    @Test(dataProvider = "AllUserDetails", dataProviderClass = CreateSubAccountTests.class, groups = {"regressionSuite"})
    public void testUserVerifyDetails(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.verifyDashboard(userFirstName, userSecondName);
        a.navigateToProfilePage();
        a.verifyProfilePage(userID, userFirstName, userSecondName);
        a.logOut();
    }
}
