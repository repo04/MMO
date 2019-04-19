package com.mmo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mmo.util.BaseClass;

public class LoginPage extends BaseClass {

    private static String username;

    /**
     * Attempts to login based on user and values from property file
     *
     * @param user
     */
    public void attemptLogin(String user) {
        username = user;
        u.verifyCurrentUrlContains(driver, xpv.getTokenValue("qaURLContains"));
        u.verifyCurrentUrlContains(driver, xpv.getTokenValue("qaURL"));
        
        WebElement userName = driver.findElement(By.xpath(xpv.getTokenValue("signInUserName")));
        WebElement passWord = driver.findElement(By.xpath(xpv.getTokenValue("signInPassword")));
        WebElement loginBtn = driver.findElement(By.xpath(xpv.getTokenValue("signInLoginButton")));

        userName.clear();
        passWord.clear();
        
        userName.sendKeys(user);
        passWord.sendKeys("");
        loginBtn.click();
        ip.isTitlePresent(driver, "MapMarker");
        u.verifyCurrentUrlContains(driver, xpv.getTokenValue("qaURL") + xpv.getTokenValue("dashBoardURL"));    
       }

    /**
     * @return userName
     */
    public static String getUser() {
        return username;
    }
}
