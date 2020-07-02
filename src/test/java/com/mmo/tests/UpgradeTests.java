package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.EmailUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UpgradeTests extends BaseClass {

    private Actions a = new Actions();
    String[] textInMessage = new String[2];


    @BeforeClass
    public void testNavigateToLogInForChangePlan() throws Exception {
        a.navigateToLogin();
    }

    //@Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUp.class)
    public void testUpgradeFreeTo5kPlan(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToBillingPlan();
        a.changePlan(userID, userFirstName, userSecondName, "5k");
    }

    //@Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUp.class)
    public void testUpgradeFreeToProfPlan(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToBillingPlan();
        a.changePlan(userID, userFirstName, userSecondName, "5k");
    }

    //@Test(dataProvider = "Paid5kUserDetails", dataProviderClass = SignUp.class)
    public void testUpgrade5kToProfPlan(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToBillingPlan();
        a.changePlan(userID, userFirstName, userSecondName, "Prof");
    }

    @Test(dataProvider = "Paid5kUserDetails", dataProviderClass = SignUp.class)
    public void testDegrade5kToFreePlanAndCheckEmail(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
        a.navigateToBillingPlan();
        a.changePlan(userID, userFirstName, userSecondName, "Free");

        textInMessage[0] = "Your MapMarker plan has been updated.";
        textInMessage[1] = "Thank you for= updating your MapMarker plan. " +
                "Please visit the below link to view your Map=Marker Dashboard.";

        emailUtils.isTextPresentInMessage("Your MapMarker subscription has been updated",
                userID, textInMessage, EmailUtils.EmailFolder.PLANCHANGE);
    }
}
