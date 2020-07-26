package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SubscriptionAdminVerifyDetailTests extends BaseClass {

    Actions a = new Actions();

    @BeforeClass(groups = {"prerequisite"})
    public void testSubscriptionUsersVerifyDetailsNavigate() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "AllSubscriptionAdminDetails", dataProviderClass = SignUp.class, groups = {"regressionSuite"})
    public void testSubscriptionAdminVerifyDetails(String userID, String userFirstName, String userSecondName) throws Exception {
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

    @Test(dataProvider = "AllPaidSubscriptionAdminDetails", dataProviderClass = SignUp.class, groups = {"regressionSuite"})
    public void testSubscriptionAdminUpdateAndVerifyCardDetails(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToBillingPlan();
        a.updateCard();
        a.logOut();
    }
}
