package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserDetailTests extends BaseClass {

    Actions a = new Actions();

    @BeforeClass()
    public void testUserNavigateToLogin() throws Exception {
        a.navigateToLogin();
    }

    //@Test(dataProvider = "ProfUserDetails", dataProviderClass = SignUp.class)
    public void testUserVerifyDetails(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.verifyDashboard(userFirstName, userSecondName);
        a.navigateToBillingPlan();
        a.verifyBillingPage(userSecondName);
    }

    @Test(dataProvider = "ProfUserDetails", dataProviderClass = SignUp.class)
    public void testUserVerifyProfileDetails(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToProfilePage();
        a.verifyProfilePage(userID, userFirstName, userSecondName);
    }


    //@Test(dataProvider = "ProfUserDetails", dataProviderClass = SignUp.class)
    public void testUserUpdateCard(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToBillingPlan();
        a.updateCard();
    }

    @AfterClass()
    public void testUserLogout() throws Exception {
        a.logOut();
    }
}
