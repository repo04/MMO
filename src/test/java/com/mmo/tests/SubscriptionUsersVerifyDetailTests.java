package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SubscriptionUsersVerifyDetailTests extends BaseClass {

    Actions a = new Actions();

    @BeforeClass(groups = {"prerequisite"})
    public void testVerifyUserDetailsNavigate() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "AllUserDetails", dataProviderClass = SignUp.class, groups = {"regressionSuite"})
    public void testSubscriptionUserVerifyDetailTest(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.verifyDashboard(userFirstName, userSecondName);
        a.navigateToProfilePage();
        a.verifyProfilePage(userID, userFirstName, userSecondName);
        a.navigateToUsers();
        a.verifyUserDetailInUsersList(userID, userFirstName, userSecondName, "SubscriptionAdmin");
        a.navigateToBillingPlan();
        a.verifyBillingPage(userSecondName);
        a.logOut();
    }


    @Test(dataProvider = "PaidUserDetails", dataProviderClass = SignUp.class, groups = {"regressionSuite"})
    public void testUserUpdateAndVerifyCardDetails(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToBillingPlan();
        a.updateCard();
        a.logOut();
    }
}
