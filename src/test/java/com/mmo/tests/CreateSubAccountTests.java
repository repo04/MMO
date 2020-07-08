package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.EmailUtils;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateSubAccountTests extends BaseClass {

    private Actions a = new Actions();
    static String[][] subscriptionAdminCreateAdminArray = new String[1][3];
    static String[][] subscriptionAdminCreateUserArray = new String[1][3];
    static String[][] adminCreateAdminArray = new String[1][3];
    static String[][] adminCreateUserArray = new String[1][3];

    @DataProvider(name = "subscriptionAdminCreateAdminDetails")
    public static Object[][] subscriptionAdminCreateAdminDetails(ITestContext context) throws Exception {
        System.out.println("init subscriptionAdminCreateAdminDetails");
        return (subscriptionAdminCreateAdminArray);
    }

    @DataProvider(name = "subscriptionAdminCreateUserDetails")
    public static Object[][] subscriptionAdminCreateUserDetails(ITestContext context) throws Exception {
        System.out.println("init subscriptionAdminCreateUserDetails");
        return (subscriptionAdminCreateUserArray);
    }

    @DataProvider(name = "adminCreateAdminDetails")
    public static Object[][] adminCreateAdminDetails(ITestContext context) throws Exception {
        System.out.println("init adminCreateAdminDetails");
        return (adminCreateAdminArray);
    }

    @DataProvider(name = "adminCreateUserDetails")
    public static Object[][] adminCreateUserDetails(ITestContext context) throws Exception {
        System.out.println("init adminCreateUserDetails");
        return (adminCreateUserArray);
    }

    @Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUp.class)
    public void testSubscriptionAdminCreateAdmin(String userID, String userFirstName, String userSecondName) throws Exception {
        a.navigateToLogin();
        a.login(userID);
        subscriptionAdminCreateAdminArray = a.createUser("Admin");
        Reporter.log("subAdminCreateAdmin_UserID: " + subscriptionAdminCreateAdminArray[0][0], true);
        Reporter.log("subAdminCreateAdmin_FirstName: " + subscriptionAdminCreateAdminArray[0][1], true);
        Reporter.log("subAdminCreateAdmin_SecondName: " + subscriptionAdminCreateAdminArray[0][2], true);
        Thread.sleep(10000);
        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", subscriptionAdminCreateAdminArray[0][0], EmailUtils.EmailFolder.SUBUSERS);
        a.completeRegistration(subscriptionAdminCreateAdminArray[0][0], subscriptionAdminCreateAdminArray[0][1], subscriptionAdminCreateAdminArray[0][2]  , claimTokenID);
    }

    @Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUp.class)
    public void testSubscriptionAdminCreateUser(String userID, String userFirstName, String userSecondName) throws Exception {
        a.navigateToLogin();
        a.login(userID);
        subscriptionAdminCreateUserArray = a.createUser("User");
        a.logOut();
        Reporter.log("subAdminCreateUser_UserID: " + subscriptionAdminCreateUserArray[0][0], true);
        Reporter.log("subAdminCreateUser_FirstName: " + subscriptionAdminCreateUserArray[0][1], true);
        Reporter.log("subAdminCreateUser_SecondName: " + subscriptionAdminCreateUserArray[0][2], true);
        Thread.sleep(10000);
        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", subscriptionAdminCreateUserArray[0][0], EmailUtils.EmailFolder.SUBUSERS);
        a.completeRegistration(subscriptionAdminCreateUserArray[0][0], subscriptionAdminCreateUserArray[0][1], subscriptionAdminCreateUserArray[0][2]  , claimTokenID);
    }

    @Test(dataProvider = "subscriptionAdminCreateAdminDetails")
    public void testAdminCreateAdmin(String userID, String userFirstName, String userSecondName) throws Exception {
        a.navigateToLogin();
        a.login(userID);
        adminCreateAdminArray = a.createUser("Admin");
        Reporter.log("adminCreateAdmin_UserID: " + adminCreateAdminArray[0][0], true);
        Reporter.log("adminCreateAdmin_FirstName: " + adminCreateAdminArray[0][1], true);
        Reporter.log("adminCreateAdmin_SecondName: " + adminCreateAdminArray[0][2], true);
        Thread.sleep(10000);
        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", adminCreateAdminArray[0][0], EmailUtils.EmailFolder.SUBUSERS);
        a.completeRegistration(adminCreateAdminArray[0][0], adminCreateAdminArray[0][1], adminCreateAdminArray[0][2]  , claimTokenID);
    }

    @Test(dataProvider = "subscriptionAdminCreateAdminDetails")
    public void testAdminCreateUser(String userID, String userFirstName, String userSecondName) throws Exception {
        a.navigateToLogin();
        a.login(userID);
        adminCreateUserArray = a.createUser("User");
        a.logOut();
        Reporter.log("adminCreateUser_UserID: " + adminCreateUserArray[0][0], true);
        Reporter.log("adminCreateUser_FirstName: " + adminCreateUserArray[0][1], true);
        Reporter.log("adminCreateUser_SecondName: " + adminCreateUserArray[0][2], true);
        Thread.sleep(10000);
        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", adminCreateUserArray[0][0], EmailUtils.EmailFolder.SUBUSERS);
        a.completeRegistration(adminCreateUserArray[0][0], adminCreateUserArray[0][1], adminCreateUserArray[0][2]  , claimTokenID);
    }
}
