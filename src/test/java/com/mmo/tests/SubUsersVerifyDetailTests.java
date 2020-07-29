package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SubUsersVerifyDetailTests extends BaseClass {

    Actions a = new Actions();
    private String downloadDefaultTemplates[] = new String[6];

    @BeforeClass(groups = {"prerequisite"})
    public void testSubUsersVerifyDetailsNavigate() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "AllAdminDetails", dataProviderClass = CreateSubAccountTests.class, groups = {"regressionSuite"})
    public void testAdminVerifyDetails(String userID, String userFirstName, String userSecondName) throws Exception {
        a.verifyLoginPage();

        a.login(userID);
        a.verifyDashboard(userFirstName, userSecondName);

        ip.invisibilityOfElementByXpath(driver, "//a[contains(text(),'Billing & Plans')]");

        downloadDefaultTemplates[0] = "FRWD CSV";
        downloadDefaultTemplates[1] = "FRWD SHP";
        downloadDefaultTemplates[2] = "FRWD TAB";
        downloadDefaultTemplates[3] = "RVRS CSV";
        downloadDefaultTemplates[4] = "RVRS SHP";
        downloadDefaultTemplates[5] = "RVRS TAB";
        a.downloadAllDefaultTemplates(downloadDefaultTemplates);

        a.navigateToProfilePage();
        a.verifyProfilePage(userID, userFirstName, userSecondName);

        a.navigateToUsers();
        a.verifyUserDetailInUsersList(userID, userFirstName, userSecondName, "Admin");

        a.logOut();
    }

    @Test(dataProvider = "AllUserDetails", dataProviderClass = CreateSubAccountTests.class, groups = {"regressionSuite"})
    public void testUserVerifyDetails(String userID, String userFirstName, String userSecondName) throws Exception {
        a.verifyLoginPage();

        a.login(userID);
        a.verifyDashboard(userFirstName, userSecondName);

        ip.invisibilityOfElementByXpath(driver, "//a[contains(text(),'Users')]");
        ip.invisibilityOfElementByXpath(driver, "//a[contains(text(),'Billing & Plans')]");

        downloadDefaultTemplates[0] = "FRWD CSV";
        downloadDefaultTemplates[1] = "FRWD SHP";
        downloadDefaultTemplates[2] = "FRWD TAB";
        downloadDefaultTemplates[3] = "RVRS CSV";
        downloadDefaultTemplates[4] = "RVRS SHP";
        downloadDefaultTemplates[5] = "RVRS TAB";
        a.downloadAllDefaultTemplates(downloadDefaultTemplates);

        a.navigateToProfilePage();
        a.verifyProfilePage(userID, userFirstName, userSecondName);

        a.logOut();
    }
}
