package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddProductFlowTests extends BaseClass {
    private String[] textInMessage;
    static String[][] geoTaxUserIDArray = new String[1][1];
    private Actions a = new Actions();

    private void testSignUpGeoTaxUserAndCompleteEmailRegistration() throws Exception {
//        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.STARTUSINGGEOTAX);
        a.navigateToGeoTaxPage();
        geoTaxUserIDArray[0][0] =  a.signUpGeoTaxUser();
        System.out.println("ID testSignUpGeoTaxUserAndCompleteEmailRegistration: " + geoTaxUserIDArray[0][0]);
        Reporter.log("geoTaxUserID: " + geoTaxUserIDArray[0][0] + "<br/>", true);
//        emailUtils.waitForEmailReceived("You're ready to start using GeoTAX", EmailUtils.EmailFolder.STARTUSINGGEOTAX, 1);
//        String claimTokenID = emailUtils.getToken("You're ready to start using GeoTAX", geoTaxUserIDArray[0][0], EmailUtils.EmailFolder.STARTUSINGGEOTAX);
//        a.completeRegistration(geoTaxUserIDArray[0][0], u.getFirstName(geoTaxUserIDArray[0][0]), u.getSecondName(geoTaxUserIDArray[0][0]), claimTokenID);

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
//        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.STARTUSINGMMO);
        a.navigateToLogin();
        a.enterLoginDetailsOnly(userID);
        a.navigateToHomeAndVerifyAddProductFlowInitiated();
        a.signUpUser("free", "US", userID);
        Reporter.log("Add Product Flow verified through Login screen <br/>", true);
//        textInMessage = new String[2];
//        textInMessage[0] = "You now have access to MapMarker.";
//        textInMessage[1] = "Your registra=tion is complete. To access MapMarker " + "all you need to do is sign in with yo=ur email address: " + userID.toLowerCase();
//        emailUtils.isTextPresentInMessage("You're ready to start using MapMarker", userID, textInMessage, EmailUtils.EmailFolder.STARTUSINGMMO);
//        Reporter.log("Add Product Flow through Login screen - email verified <br/>", true);
    }

    //@Test(dataProvider = "GeoTaxUserID", groups = {"regressionSuite", "sanitySuite"})
    public void testAddProductFlowThroughSignUpScreenCheckAccessEmail(String userID) throws Exception {
//        emailUtils.deleteAllEmails(EmailUtils.EmailFolder.STARTUSINGMMO);
        a.navigateToSignUpForAddProductFlow("free", userID);
        a.enterLoginDetailsOnly(userID);
        a.signUpUser("free", "US", userID);
        Reporter.log("Add Product Flow verified through SignUp screen <br/>", true);
//        textInMessage = new String[2];
//        textInMessage[0] = "You now have access to MapMarker.";
//
//        textInMessage[1] = "Your registra=tion is complete. To access MapMarker " + "all you need to do is sign in with yo=ur email address: " + userID.toLowerCase();
//        emailUtils.isTextPresentInMessage("You're ready to start using MapMarker", userID, textInMessage, EmailUtils.EmailFolder.STARTUSINGMMO);
//        Reporter.log("Add Product Flow through SignUp screen - email verified <br/>", true);
    }
}