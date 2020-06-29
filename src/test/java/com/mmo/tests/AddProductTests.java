package com.mmo.tests;

import com.mmo.util.BaseClass;
import org.testng.annotations.Test;
import com.mmo.util.Actions;


public class AddProductTests extends BaseClass {
    public Actions a = new Actions();

    @Test(dataProvider = "GeoTaxUserDetails", dataProviderClass = SignUp.class)
    public void testAddProductFlowThroughLoginScreen(String x, String y, String z) throws Exception {
        a.navigateToLogin();
        a.enterLoginDetailsOnly(x);
        a.navigateToHomeAndAddProductFlowInitiated();
        a.signUpUser("free", "US", x);
    }
}
