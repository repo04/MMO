package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class CreateSubAccountTests extends BaseClass {

    private Actions a = new Actions();
    static String[][] subscriptionAdminCreateAdminArray = new String[1][3];
    static String[][] subscriptionAdminCreateUserArray = new String[1][3];
    static String[][] adminCreateAdminArray = new String[1][3];
    static String[][] adminCreateUserArray = new String[1][3];
    int sa, su, aa, au = 0;

    @DataProvider(name = "SubscriptionAdminCreateAdminDetails")
    public static Object[][] SubscriptionAdminCreateAdminDetails(ITestContext context) throws Exception {
        System.out.println("init SubscriptionAdminCreateAdminDetails");
//        subscriptionAdminCreateAdminArray[0][0] = "mmoAutomated+FreeUSSA_Admin080421140959@gmail.com";
//        subscriptionAdminCreateAdminArray[0][1] = "mmoAutomated";
//        subscriptionAdminCreateAdminArray[0][2] = "FreeUSSA_Admin080421140959";
        return subscriptionAdminCreateAdminArray;
    }

    @DataProvider(name = "SubscriptionAdminCreateUserDetails")
    public static Object[][] SubscriptionAdminCreateUserDetails(ITestContext context) throws Exception {
        System.out.println("init SubscriptionAdminCreateUserDetails");
//        subscriptionAdminCreateUserArray[0][0] = "mmoAutomated+FreeUSSA_User080421141112@gmail.com";
//        subscriptionAdminCreateUserArray[0][1] = "mmoAutomated";
//        subscriptionAdminCreateUserArray[0][2] = "FreeUSSA_User080421141112";
        return subscriptionAdminCreateUserArray;
    }

    @DataProvider(name = "AdminCreateAdminDetails")
    public static Object[][] AdminCreateAdminDetails(ITestContext context) throws Exception {
        System.out.println("init AdminCreateAdminDetails");
//        adminCreateAdminArray[0][0] = "mmoAutomated+FreeUSAd_Admin080421141223@gmail.com";
//        adminCreateAdminArray[0][1] = "mmoAutomated";
//        adminCreateAdminArray[0][2] = "FreeUSAd_Admin080421141223";
        return adminCreateAdminArray;
    }

    @DataProvider(name = "AdminCreateUserDetails")
    public static Object[][] AdminCreateUserDetails(ITestContext context) throws Exception {
        System.out.println("init AdminCreateUserDetails");
//        adminCreateUserArray[0][0] = "mmoAutomated+FreeUSAd_User080421141339@gmail.com";
//        adminCreateUserArray[0][1] = "mmoAutomated";
//        adminCreateUserArray[0][2] = "FreeUSAd_User080421141339";
        return adminCreateUserArray;
    }

    @DataProvider(name = "AllAdminDetails")
    public static Object[][] AllAdminDetails(ITestContext context) throws Exception {
        System.out.println("init AllAdminDetails");
        return DataProviderUtility.append2DArrayVertically(SubscriptionAdminCreateAdminDetails(context), AdminCreateAdminDetails(context));
    }

    @DataProvider(name = "AllUserDetails")
    public static Object[][] AllUserDetails(ITestContext context) throws Exception {
        System.out.println("init AllUserDetails");
        return DataProviderUtility.append2DArrayVertically(SubscriptionAdminCreateUserDetails(context), AdminCreateUserDetails(context));
    }

    @DataProvider(name = "SubscriptionAdminCreateAdminSubIdDetails")
    public static Iterator<Object[]> SubscriptionAdminCreateAdminSubIdDetails(ITestContext context) throws Exception {
        System.out.println("init SubscriptionAdminCreateAdminSubIdDetails");
        return DataProviderUtility.cartesianProviderFrom(SubscriptionAdminCreateAdminDetails(context), SignUpTests.UserSubID(context));
    }

    @BeforeClass(groups = {"prerequisite"})
    public void testUserNavigateToLogin() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "FreeUSUserSubIdDetails", dataProviderClass = SignUpTests.class, groups = {"regressionSuite"})
    public void testSubscriptionAdminCreateAdmin(String userID, String userFirstName, String userSecondName, String subID) throws Exception {
//        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.SUBUSERS);
        a.login(userID);
        a.navigateToCreateUser();
//        subscriptionAdminCreateAdminArray[sa][0] = a.createUser(userSecondName, "SA_Admin");
        subscriptionAdminCreateAdminArray[sa][0] = a.createUserThroughAPI(userSecondName, "SA_Admin", bearerTokenID, subID);
        subscriptionAdminCreateAdminArray[sa][1] = subscriptionAdminCreateAdminArray[sa][0].substring(0, subscriptionAdminCreateAdminArray[sa][0].indexOf("+"));
        subscriptionAdminCreateAdminArray[sa][2] = subscriptionAdminCreateAdminArray[sa][0].substring(subscriptionAdminCreateAdminArray[sa][0].indexOf("+") + 1,
                subscriptionAdminCreateAdminArray[sa][0].indexOf("@"));
        Reporter.log("SubcAdminCreateAdmin_UserID: " + subscriptionAdminCreateAdminArray[sa][0] + "<br/>", true);
        a.navigateToUsers();
        a.verifyUserDetailInUsersList(subscriptionAdminCreateAdminArray[sa][0], subscriptionAdminCreateAdminArray[sa][1], subscriptionAdminCreateAdminArray[sa][2], "Admin");
        a.logOut();
//        emailUtils.waitForEmailReceived("Welcome! Get started with Precisely MapMarker", EmailUtils.EmailFolder.SUBUSERS, 1);
//        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", subscriptionAdminCreateAdminArray[sa][0], EmailUtils.EmailFolder.SUBUSERS);
//        a.completeRegistration(subscriptionAdminCreateAdminArray[sa][0], subscriptionAdminCreateAdminArray[sa][1], subscriptionAdminCreateAdminArray[sa][2]  , claimTokenID);
        sa++;
    }

    @Test(dataProvider = "FreeUSUserSubIdDetails", dataProviderClass = SignUpTests.class, groups = {"regressionSuite"})
    public void testSubscriptionAdminCreateUser(String userID, String userFirstName, String userSecondName, String subID) throws Exception {
//        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.SUBUSERS);
        a.login(userID);
        a.navigateToCreateUser();
//        subscriptionAdminCreateUserArray[su][0] = a.createUser(userSecondName, "SA_User");
        subscriptionAdminCreateUserArray[su][0] = a.createUserThroughAPI(userSecondName, "SA_User", bearerTokenID, subID);
        subscriptionAdminCreateUserArray[su][1] = subscriptionAdminCreateUserArray[su][0].substring(0, subscriptionAdminCreateUserArray[su][0].indexOf("+"));
        subscriptionAdminCreateUserArray[su][2] = subscriptionAdminCreateUserArray[su][0].substring(subscriptionAdminCreateUserArray[su][0].indexOf("+") + 1,
                subscriptionAdminCreateUserArray[su][0].indexOf("@"));
        Reporter.log("SubcAdminCreateUser_UserID: " + subscriptionAdminCreateUserArray[su][0] + "<br/>", true);
        a.navigateToUsers();
        a.verifyUserDetailInUsersList(subscriptionAdminCreateUserArray[su][0], subscriptionAdminCreateUserArray[su][1], subscriptionAdminCreateUserArray[su][2], "User");
        a.logOut();
//        emailUtils.waitForEmailReceived("Welcome! Get started with Precisely MapMarker", EmailUtils.EmailFolder.SUBUSERS, 1);
//        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", subscriptionAdminCreateUserArray[su][0], EmailUtils.EmailFolder.SUBUSERS);
//        a.completeRegistration(subscriptionAdminCreateUserArray[su][0], subscriptionAdminCreateUserArray[su][1], subscriptionAdminCreateUserArray[su][2] , claimTokenID);
        su++;
    }

    @Test(dataProvider = "SubscriptionAdminCreateAdminSubIdDetails", groups = {"regressionSuite"})
    public void testAdminCreateAdmin(String userID, String userFirstName, String userSecondName, String subID) throws Exception {
//        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.SUBUSERS);
        a.login(userID);
        a.navigateToCreateUser();
//        adminCreateAdminArray[aa][0] = a.createUser(userSecondName, "Ad_Admin");
        adminCreateAdminArray[aa][0] = a.createUserThroughAPI(userSecondName, "Ad_Admin", bearerTokenID, subID);
        adminCreateAdminArray[aa][1] = adminCreateAdminArray[aa][0].substring(0, adminCreateAdminArray[aa][0].indexOf("+"));
        adminCreateAdminArray[aa][2] = adminCreateAdminArray[aa][0].substring(adminCreateAdminArray[aa][0].indexOf("+") + 1,
                adminCreateAdminArray[aa][0].indexOf("@"));
        Reporter.log("AdminCreateAdmin_UserID: " + adminCreateAdminArray[aa][0] + "<br/>", true);
        a.navigateToUsers();
        a.verifyUserDetailInUsersList(adminCreateAdminArray[aa][0], adminCreateAdminArray[aa][1], adminCreateAdminArray[aa][2], "Admin");
        a.logOut();
//        emailUtils.waitForEmailReceived("Welcome! Get started with Precisely MapMarker", EmailUtils.EmailFolder.SUBUSERS, 1);
//        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", adminCreateAdminArray[aa][0], EmailUtils.EmailFolder.SUBUSERS);
//        a.completeRegistration(adminCreateAdminArray[aa][0], adminCreateAdminArray[aa][1], adminCreateAdminArray[aa][2]  , claimTokenID);
        aa++;
    }

    @Test(dataProvider = "SubscriptionAdminCreateAdminSubIdDetails", groups = {"regressionSuite"})
    public void testAdminCreateUser(String userID, String userFirstName, String userSecondName, String subID) throws Exception {
//        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.SUBUSERS);
        a.login(userID);
        a.navigateToCreateUser();
//        adminCreateUserArray[au][0] = a.createUser(userSecondName, "Ad_User");
        adminCreateUserArray[au][0] = a.createUserThroughAPI(userSecondName, "Ad_User", bearerTokenID, subID);
        adminCreateUserArray[au][1] = adminCreateUserArray[au][0].substring(0, adminCreateUserArray[au][0].indexOf("+"));
        adminCreateUserArray[au][2] = adminCreateUserArray[au][0].substring(adminCreateUserArray[au][0].indexOf("+") + 1,
                adminCreateUserArray[au][0].indexOf("@"));
        Reporter.log("AdminCreateUser_UserID: " + adminCreateUserArray[au][0] + "<br/>", true);
        a.navigateToUsers();
        a.verifyUserDetailInUsersList(adminCreateUserArray[au][0], adminCreateUserArray[au][1], adminCreateUserArray[au][2], "User");
        a.logOut();
//        emailUtils.waitForEmailReceived("Welcome! Get started with Precisely MapMarker", EmailUtils.EmailFolder.SUBUSERS, 1);
//        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", adminCreateUserArray[au][0], EmailUtils.EmailFolder.SUBUSERS);
//        a.completeRegistration(adminCreateUserArray[au][0], adminCreateUserArray[au][1], adminCreateUserArray[au][2]  , claimTokenID);
        au++;
    }
}