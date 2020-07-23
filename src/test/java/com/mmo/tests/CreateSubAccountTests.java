package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import com.mmo.util.EmailUtils;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateSubAccountTests extends BaseClass {

    private Actions a = new Actions();
    static String[][] subscriptionAdminCreateAdminArray = new String[4][3];
    static String[][] subscriptionAdminCreateUserArray = new String[4][3];
    static String[][] adminCreateAdminArray = new String[4][3];
    static String[][] adminCreateUserArray = new String[4][3];
    int sa, su, aa, au = 0;

    @DataProvider(name = "subscriptionAdminCreateAdminDetails")
    public static Object[][] subscriptionAdminCreateAdminDetails(ITestContext context) throws Exception {
        System.out.println("init subscriptionAdminCreateAdminDetails");
        subscriptionAdminCreateAdminArray[0][0] = "mmoAutomated+FreeNonUS_Admin230720175705@gmail.com";
        subscriptionAdminCreateAdminArray[0][1] = "mmoAutomated";
        subscriptionAdminCreateAdminArray[0][2] = "FreeNonUS_Admin230720175705";
        subscriptionAdminCreateAdminArray[1][0] = "mmoAutomated+FreeUS_Admin230720175810@gmail.com";
        subscriptionAdminCreateAdminArray[1][1] = "mmoAutomated";
        subscriptionAdminCreateAdminArray[1][2] = "FreeUS_Admin230720175810";
        subscriptionAdminCreateAdminArray[2][0] = "mmoAutomated+5k_Admin230720175911@gmail.com";
        subscriptionAdminCreateAdminArray[2][1] = "mmoAutomated";
        subscriptionAdminCreateAdminArray[2][2] = "5k_Admin230720175911";
        subscriptionAdminCreateAdminArray[3][0] = "mmoAutomated+Prof_Admin230720180012@gmail.com";
        subscriptionAdminCreateAdminArray[3][1] = "mmoAutomated";
        subscriptionAdminCreateAdminArray[3][2] = "Prof_Admin230720180012";
        return (subscriptionAdminCreateAdminArray);
    }

    @DataProvider(name = "subscriptionAdminCreateUserDetails")
    public static Object[][] subscriptionAdminCreateUserDetails(ITestContext context) throws Exception {
        System.out.println("init subscriptionAdminCreateUserDetails");
        subscriptionAdminCreateUserArray[0][0] = "mmoAutomated+FreeNonUS_User230720180113@gmail.com";
        subscriptionAdminCreateUserArray[0][1] = "mmoAutomated";
        subscriptionAdminCreateUserArray[0][2] = "FreeNonUS_User230720180113";
        subscriptionAdminCreateUserArray[1][0] = "mmoAutomated+FreeUS_User230720180212@gmail.com";
        subscriptionAdminCreateUserArray[1][1] = "mmoAutomated";
        subscriptionAdminCreateUserArray[1][2] = "FreeUS_User230720180212";
        subscriptionAdminCreateUserArray[2][0] = "mmoAutomated+5k_User230720180313@gmail.com";
        subscriptionAdminCreateUserArray[2][1] = "mmoAutomated";
        subscriptionAdminCreateUserArray[2][2] = "5k_User230720180313";
        subscriptionAdminCreateUserArray[3][0] = "mmoAutomated+Prof_User230720180412@gmail.com";
        subscriptionAdminCreateUserArray[3][1] = "mmoAutomated";
        subscriptionAdminCreateUserArray[3][2] = "Prof_User230720180412";
        return (subscriptionAdminCreateUserArray);
    }

    @DataProvider(name = "adminCreateAdminDetails")
    public static Object[][] adminCreateAdminDetails(ITestContext context) throws Exception {
        System.out.println("init adminCreateAdminDetails");
        adminCreateAdminArray[0][0] = "mmoAutomated+FreeNonUS_Admin230720180513@gmail.com";
        adminCreateAdminArray[0][1] = "mmoAutomated";
        adminCreateAdminArray[0][2] = "FreeNonUS_Admin230720180513";
        adminCreateAdminArray[1][0] = "mmoAutomated+FreeUS_Admin230720180624@gmail.com";
        adminCreateAdminArray[1][1] = "mmoAutomated";
        adminCreateAdminArray[1][2] = "FreeUS_Admin230720180624";
        adminCreateAdminArray[2][0] = "mmoAutomated+5k_Admin230720180733@gmail.com";
        adminCreateAdminArray[2][1] = "mmoAutomated";
        adminCreateAdminArray[2][2] = "5k_Admin230720180733";
        adminCreateAdminArray[3][0] = "mmoAutomated+Prof_Admin230720180842@gmail.com";
        adminCreateAdminArray[3][1] = "mmoAutomated";
        adminCreateAdminArray[3][2] = "Prof_Admin230720180842";
        return (adminCreateAdminArray);
    }

    @DataProvider(name = "adminCreateUserDetails")
    public static Object[][] adminCreateUserDetails(ITestContext context) throws Exception {
        System.out.println("init adminCreateUserDetails");
        adminCreateUserArray[0][0] = "mmoAutomated+FreeNonUS_User230720180953@gmail.com";
        adminCreateUserArray[0][1] = "mmoAutomated";
        adminCreateUserArray[0][2] = "FreeNonUS_User230720180953";
        adminCreateUserArray[1][0] = "mmoAutomated+FreeUS_User230720181055@gmail.com";
        adminCreateUserArray[1][1] = "mmoAutomated";
        adminCreateUserArray[1][2] = "FreeUS_User230720181055";
        adminCreateUserArray[2][0] = "mmoAutomated+5k_User230720181155@gmail.com";
        adminCreateUserArray[2][1] = "mmoAutomated";
        adminCreateUserArray[2][2] = "5k_User230720181155";
        adminCreateUserArray[3][0] = "mmoAutomated+Prof_User230720181256@gmail.com";
        adminCreateUserArray[3][1] = "mmoAutomated";
        adminCreateUserArray[3][2] = "Prof_User230720181256";
        return (adminCreateUserArray);
    }

    @DataProvider(name = "AllAdminDetails")
    public static Object[][] AllAdminDetails(ITestContext context) throws Exception {
        System.out.println("init AllAdminDetails");
        return DataProviderUtility.multiIterationData2(subscriptionAdminCreateAdminDetails(context), adminCreateAdminDetails(context));
    }

    @DataProvider(name = "AllUserDetails")
    public static Object[][] AllUserDetails(ITestContext context) throws Exception {
        System.out.println("init AllUserDetails");
        return DataProviderUtility.multiIterationData2(subscriptionAdminCreateUserDetails(context), adminCreateUserDetails(context));
    }

    @BeforeClass(groups = {"prerequisite"})
    public void testUserNavigateToLogin() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "AllUserDetails", dataProviderClass = SignUp.class, groups = {"regressionSuite"})
    public void testSubscriptionAdminCreateAdmin(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToCreateUser();
        subscriptionAdminCreateAdminArray[sa][0] = a.createUser(userSecondName, "Admin");
        subscriptionAdminCreateAdminArray[sa][1] = subscriptionAdminCreateAdminArray[sa][0].substring(0, subscriptionAdminCreateAdminArray[sa][0].indexOf("+"));
        subscriptionAdminCreateAdminArray[sa][2] = subscriptionAdminCreateAdminArray[sa][0].substring(subscriptionAdminCreateAdminArray[sa][0].indexOf("+") + 1,
                subscriptionAdminCreateAdminArray[sa][0].indexOf("@"));

        Reporter.log("SubcAdminCreateAdmin_UserID: " + subscriptionAdminCreateAdminArray[sa][0], true);
        a.navigateToUsers();
        a.verifyUserDetailInUsersList(subscriptionAdminCreateAdminArray[sa][0], subscriptionAdminCreateAdminArray[sa][1], subscriptionAdminCreateAdminArray[sa][2], "Admin");
        a.logOut();
        Thread.sleep(10000);
        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", subscriptionAdminCreateAdminArray[sa][0], EmailUtils.EmailFolder.SUBUSERS);
        a.completeRegistration(subscriptionAdminCreateAdminArray[sa][0], subscriptionAdminCreateAdminArray[sa][1], subscriptionAdminCreateAdminArray[sa][2]  , claimTokenID);
        sa++;
    }

    @Test(dataProvider = "AllUserDetails", dataProviderClass = SignUp.class, groups = {"regressionSuite"})
    public void testSubscriptionAdminCreateUser(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToCreateUser();
        subscriptionAdminCreateUserArray[su][0] = a.createUser(userSecondName, "User");
        subscriptionAdminCreateUserArray[su][1] = subscriptionAdminCreateUserArray[su][0].substring(0, subscriptionAdminCreateUserArray[su][0].indexOf("+"));
        subscriptionAdminCreateUserArray[su][2] = subscriptionAdminCreateUserArray[su][0].substring(subscriptionAdminCreateUserArray[su][0].indexOf("+") + 1,
                subscriptionAdminCreateUserArray[su][0].indexOf("@"));

        Reporter.log("SubcAdminCreateUser_UserID: " + subscriptionAdminCreateUserArray[su][0], true);
        a.navigateToUsers();
        a.verifyUserDetailInUsersList(subscriptionAdminCreateUserArray[su][0], subscriptionAdminCreateUserArray[su][1], subscriptionAdminCreateUserArray[su][2], "User");
        a.logOut();
        Thread.sleep(10000);
        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", subscriptionAdminCreateUserArray[su][0], EmailUtils.EmailFolder.SUBUSERS);
        a.completeRegistration(subscriptionAdminCreateUserArray[su][0], subscriptionAdminCreateUserArray[su][1], subscriptionAdminCreateUserArray[su][2]  , claimTokenID);
        su++;
    }

    @Test(dataProvider = "subscriptionAdminCreateAdminDetails", groups = {"regressionSuite"})
    public void testAdminCreateAdmin(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToCreateUser();
        adminCreateAdminArray[aa][0] = a.createUser(userSecondName, "Admin");
        adminCreateAdminArray[aa][1] = adminCreateAdminArray[aa][0].substring(0, adminCreateAdminArray[aa][0].indexOf("+"));
        adminCreateAdminArray[aa][2] = adminCreateAdminArray[aa][0].substring(adminCreateAdminArray[aa][0].indexOf("+") + 1,
                adminCreateAdminArray[aa][0].indexOf("@"));

        Reporter.log("AdminCreateAdmin_UserID: " + adminCreateAdminArray[aa][0], true);
        a.navigateToUsers();
        a.verifyUserDetailInUsersList(adminCreateAdminArray[aa][0], adminCreateAdminArray[aa][1], adminCreateAdminArray[aa][2], "Admin");
        a.logOut();
        Thread.sleep(10000);
        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", adminCreateAdminArray[aa][0], EmailUtils.EmailFolder.SUBUSERS);
        a.completeRegistration(adminCreateAdminArray[aa][0], adminCreateAdminArray[aa][1], adminCreateAdminArray[aa][2]  , claimTokenID);
        aa++;
    }

    @Test(dataProvider = "subscriptionAdminCreateAdminDetails", groups = {"regressionSuite"})
    public void testAdminCreateUser(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToCreateUser();
        adminCreateUserArray[au][0] = a.createUser(userSecondName, "User");
        adminCreateUserArray[au][1] = adminCreateUserArray[au][0].substring(0, adminCreateUserArray[au][0].indexOf("+"));
        adminCreateUserArray[au][2] = adminCreateUserArray[au][0].substring(adminCreateUserArray[au][0].indexOf("+") + 1,
                adminCreateUserArray[au][0].indexOf("@"));

        Reporter.log("AdminCreateUser_UserID: " + adminCreateUserArray[au][0], true);
        a.navigateToUsers();
        a.verifyUserDetailInUsersList(adminCreateUserArray[au][0], adminCreateUserArray[au][1], adminCreateUserArray[au][2], "User");
        a.logOut();
        Thread.sleep(10000);
        String claimTokenID = emailUtils.getTokenForSubUsers("Welcome! Get started with Precisely MapMarker", adminCreateUserArray[au][0], EmailUtils.EmailFolder.SUBUSERS);
        a.completeRegistration(adminCreateUserArray[au][0], adminCreateUserArray[au][1], adminCreateUserArray[au][2]  , claimTokenID);
        au++;
    }

    //@Test(dataProvider = "Paid5kUserDetails", dataProviderClass = SignUp.class)
    public void testSubscriptionAdminDeleteUser(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToUsers();
        a.deleteUser("qwer+rewq2@gmail.com");
        Reporter.log("User deleted: " + adminCreateUserArray[0][0], true);
    }
}
