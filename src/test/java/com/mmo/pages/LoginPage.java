package com.mmo.pages;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mmo.util.BaseClass;

public class LoginPage extends BaseClass {

    private static String username;

    /**
     * 
     */
    public void verifyLoginPage() {
    	ip.isTitlePresent(driver, "Precisely");
    	ip.isElementClickableByXpath(driver, "//html/body/app-root/div/app-login/div/a", 60);
		driver.findElement(By.xpath("//html/body/app-root/div/app-login/div/a")).click();
		ip.isTitlePresent(driver, "Precisely - Trust your data. Build your possibilities.");
		driver.navigate().back();
		ip.isTitlePresent(driver, "Precisely");
    	ip.isTextPresentByXPATH(driver, "//h1", "MapMarker");
		ip.isElementPresentByXPATH(driver, "//div[2]/label");
		assertEquals(driver.findElement(By.xpath("//label/span")).getText(), "Remember me");
		assertEquals(driver.findElement(By.xpath("//p/a")).getText(), "Forgot your password?");
		assertEquals(driver.findElement(By.xpath("//div/div/label")).getText(), "Not a registered user?");
		assertEquals(driver.findElement(By.xpath("//div/div/div/a")).getText(), "Sign up now");
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
        WebElement passWord = driver.findElement(By.xpath(xpv.getTokenValue("signInPassword")));
        WebElement loginBtn = driver.findElement(By.xpath(xpv.getTokenValue("signInLoginButton")));

        userName.clear();
        passWord.clear();
        
        userName.sendKeys(user);
        passWord.sendKeys("Pitney@123");
        loginBtn.click();
        ip.isTitlePresent(driver, "MapMarker");
        System.out.println("USER DASHBOARD");
        ip.isElementClickableByXpath(driver, "//a/div/div", 60);
//        ip.isTextPresentByXPATH(driver, "//a/div/div", "chetan shamdasani");
        System.out.println("USER DETAILES VERIFIED");
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
