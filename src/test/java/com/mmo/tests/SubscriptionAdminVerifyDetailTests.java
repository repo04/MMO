package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SubscriptionAdminVerifyDetailTests extends BaseClass {

    Actions a = new Actions();
    private String downloadDefaultTemplates[] = new String[6];

    @BeforeClass(groups = {"prerequisite"})
    public void testSubscriptionAdminNavigateAndVerifyLoginPage() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
        a.verifyLoginPage();
        a.verifyLoginPageFooters();
    }

    //@Test(dataProvider = "AllSubscriptionAdminDetails", dataProviderClass = SignUpTests.class, groups = {"regressionSuite"})
    @Test(dataProvider = "AllFreeSubscriptionAdminDetails", dataProviderClass = SignUpTests.class, groups = {"regressionSuite"})
    public void testSubscriptionAdminVerifyDetails(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.verifyDashboard(userFirstName, userSecondName);

//        downloadDefaultTemplates[0] = "FRWD CSV";
//        downloadDefaultTemplates[1] = "FRWD SHP";
//        downloadDefaultTemplates[2] = "FRWD TAB";
//        downloadDefaultTemplates[3] = "RVRS CSV";
//        downloadDefaultTemplates[4] = "RVRS SHP";
//        downloadDefaultTemplates[5] = "RVRS TAB";
//        a.downloadAllDefaultTemplates(downloadDefaultTemplates);

        a.navigateToProfilePage();
        a.verifyProfilePage(userID, userFirstName, userSecondName);

        a.navigateToUsers();
        a.verifyUserDetailInUsersList(userID, userFirstName, userSecondName, "SubscriptionAdmin");

//        a.navigateToBillingPlan();
//        a.verifyBillingPage(userSecondName);

        a.logOut();
    }

    //@Test(dataProvider = "ProfUserDetails", dataProviderClass = SignUpTests.class, groups = {"regressionSuite"})
    public void testSubscriptionAdminUpdateAndVerifyCardDetails(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToBillingPlan();
        a.updateCard();
        a.logOut();
    }
}
