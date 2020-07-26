package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.EmailUtils;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class AddProductTests extends BaseClass {
    private String[] textInMessage;
    static String geoTaxUserDetails;
    static String[][] geoTaxUserIDArray = new String[1][1];
    private Actions a = new Actions();

    private void testSignUpGeoTaxUserAndCompleteEmailRegistration() throws Exception {
        a.navigateToGeoTaxPage();
        geoTaxUserDetails =  a.signUpGeoTaxUser();
        geoTaxUserIDArray[0][0] = geoTaxUserDetails;
        System.out.println("ID testSignUpGeoTaxUserAndCompleteEmailRegistration: " + geoTaxUserDetails);
        Reporter.log("geoTaxUserID: " + geoTaxUserDetails, true);
        Thread.sleep(10000);
        String claimTokenID = emailUtils.getToken("You're ready to start using GeoTAX", geoTaxUserDetails, EmailUtils.EmailFolder.STARTUSINGGEOTAX);
        a.completeRegistration(geoTaxUserDetails, u.getFirstName(geoTaxUserDetails), u.getSecondName(geoTaxUserDetails), claimTokenID);

        if(envValue.equalsIgnoreCase("qa"))
        {
            driver.get("https://" + xpv.getTokenValue("qaURL"));
        } else if(envValue.equalsIgnoreCase("ppd"))
        {
            driver.get("https://" + xpv.getTokenValue("ppdURL"));
        } else {
            driver.get("https://" + xpv.getTokenValue("prodURL"));
        }
    }

    @DataProvider(name = "GeoTaxUserID")
    public Object[][] GeoTaxUserID(ITestContext context) throws Exception {
        System.out.println("***init GeoTaxUserID***");
        testSignUpGeoTaxUserAndCompleteEmailRegistration();
        return geoTaxUserIDArray;
    }

    @AfterMethod(groups = {"prerequisite"})
    public void logOut() {
        ip.isElementPresentByXPATH(driver, xpv.getTokenValue("linkToLogOut"));
        u.clickByJavaScript(driver, xpv.getTokenValue("linkToLogOut"));
        ip.isElementClickableByXpath(driver, xpv.getTokenValue("signInUserName"), 60);
    }

    @Test(dataProvider = "GeoTaxUserID", groups = {"regressionSuite", "sanitySuite"})
    public void testAddProductFlowThroughLoginScreenCheckAccessEmail(String userID) throws Exception {
        a.navigateToLogin();
        a.enterLoginDetailsOnly(userID);
        a.navigateToHomeAndVerifyAddProductFlowInitiated();
        a.signUpUser("free", "US", userID);
        Thread.sleep(15);
        textInMessage = new String[2];
        textInMessage[0] = "You now have access to MapMarker.";
        textInMessage[1] = "Your registra=tion is complete. To access MapMarker " +
                "all you need to do is sign in with yo=ur email address: " + userID;
        emailUtils.isTextPresentInMessage("You're ready to start using MapMarker", userID, textInMessage, EmailUtils.EmailFolder.STARTUSINGMMO);
    }

    @Test(dataProvider = "GeoTaxUserID", groups = {"regressionSuite", "sanitySuite"})
    public void testAddProductFlowThroughSignUpScreenCheckAccessEmail(String userID) throws Exception {
        a.navigateToSignUpForAddProductFlow("free", userID);
        a.enterLoginDetailsOnly(userID);
        a.signUpUser("free", "US", userID);
        Thread.sleep(15);
        textInMessage = new String[2];
        textInMessage[0] = "You now have access to MapMarker.";
        textInMessage[1] = "Your registra=tion is complete. To access MapMarker " +
                "all you need to do is sign in with yo=ur email address: " + userID;
        emailUtils.isTextPresentInMessage("You're ready to start using MapMarker", userID, textInMessage, EmailUtils.EmailFolder.STARTUSINGMMO);
    }
}
