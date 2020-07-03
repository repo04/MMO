package com.mmo.pages;

import com.mmo.util.BaseClass;

public class ProfilePage extends BaseClass {

    /**
     *
     * @param userID
     * @param userFirstName
     * @param userSecondName
     */
    public void verifyProfilePage(String userID, String userFirstName, String userSecondName) {

        ip.isTextPresentByXPATH(driver, "//input[@id='firstName']", userFirstName);
        ip.isTextPresentByXPATH(driver, "//input[@id='lastName']", userSecondName);
        ip.isTextPresentByXPATH(driver, "//input[@id='lastName']", userID);
        ip.isTextPresentByXPATH(driver, "//input[@id='companyName']", "PRECISELY");
    }
}
