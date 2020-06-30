package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import org.testng.annotations.Test;

public class UpgradeTests extends BaseClass {

    public Actions a = new Actions();

    @Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUp.class)
    public void testUpgradeFreeTo5kPlan(String userID, String userFirstName, String userSecondName) throws Exception {
        a.navigateToBillingPlan();
        a.upgradePlan(userID, userFirstName, userSecondName, "5k");
    }
}
