package com.mmo.pages;

import com.mmo.util.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends BaseClass {

    private static String username;

    /**
     * 
     */
    public void verifyLoginPage() {
    	ip.isTitlePresent(driver, "MapMarker");
//        ip.isElementClickableByXpath(driver, "//a[@class='signin-logo d-block mt-2 mb-4']", 60);
//		  driver.findElement(By.xpath("//a[@class='signin-logo d-block mt-2 mb-4']")).click();
//        u.verifyWindowTitle(driver, "Precisely - Trust your data. Build your possibilities.", ip);
//        driver.navigate().back();
//        ip.isTitlePresent(driver, "MapMarker");
    	ip.isGetTextContainsByXPATH(driver, "//h1", "MapMarker");
    	ip.isElementPresentByXPATH(driver, "//div[2]/label");
        ip.isElementPresentByXPATH(driver, xpv.getTokenValue("signInUserName"));
        ip.isElementPresentByXPATH(driver, "//button[@id='nextButton']/span");
        ip.isGetTextContainsByXPATH(driver, "//label/span", "Remember me", 10);
        ip.isGetTextContainsByXPATH(driver, "//p/a", "Forgot your password?", 10);
        ip.isGetTextContainsByXPATH(driver, "//div/div/label", "Not a registered user?", 10);
        ip.isGetTextContainsByXPATH(driver, "//div/div/div/a", "Sign up now", 10);
        ip.isElementPresentByXPATH(driver, "//select[@id='localeChangeDropdown']");
    }
    
    /**
     * Attempts to login based on user and values from property file
     *
     * @param user
     */
    public void attemptLogin(String user) {
        username = user;
        
        ip.isElementClickableByXpath(driver, xpv.getTokenValue("signInUserName"), 60);
        
        WebElement userName = driver.findElement(By.xpath(xpv.getTokenValue("signInUserName")));
        userName.clear();
        userName.sendKeys(user);
        driver.findElement(By.xpath("//button[@id='nextButton']/span")).click();
        ip.isElementPresentByXPATH(driver, xpv.getTokenValue("signInPassword"));
        ip.isElementPresentByXPATH(driver, xpv.getTokenValue("signInLoginButton"));
        WebElement passWord = driver.findElement(By.xpath(xpv.getTokenValue("signInPassword")));
        passWord.clear();
        WebElement loginBtn = driver.findElement(By.xpath(xpv.getTokenValue("signInLoginButton")));

        passWord.sendKeys("Precisely@123");
        loginBtn.click();
//        u.waitTillSpinnerDisable(driver, "//div[starts-with(@class,'spinner-sample')]");
        ip.isTitlePresent(driver, "MapMarker");
        ip.isElementClickableByXpath(driver, "//a/div/div", 60);

        int plusIndex, attherateIndex;
        plusIndex = user.indexOf("+");
        attherateIndex = user.indexOf("@");

        //ip.isGetTextContainsByXPATH(driver, "//a/div/div", user.substring(0, plusIndex) + " " + user.substring(plusIndex + 1, attherateIndex));
    }

    /**
     *
     * @param userID
     */
    public void enterLoginDetailsOnly(String userID) {
        ip.isElementClickableByXpath(driver, xpv.getTokenValue("signInUserName"), 60);
        driver.findElement(By.xpath(xpv.getTokenValue("signInUserName"))).sendKeys(userID);
        driver.findElement(By.xpath(xpv.getTokenValue("signInPassword"))).sendKeys("Precisely@123");
        driver.findElement(By.xpath(xpv.getTokenValue("signInLoginButton"))).click();
    }

    /**
     * @return userName
     */
    public static String getUser() {
        return username;
    }


}
