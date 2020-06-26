package com.mmo.pages;

import org.openqa.selenium.By;

import com.mmo.util.BaseClass;

public class EmailPage extends BaseClass {

    public void completeRegisteration(String signUpFreeUSUserID, String signUpFreeUSUserFirstName, String signUpFreeUSUserSecondName, String claimTokenID) {
    	System.out.println("CLAIM ID: https://login-qa.saas.precisely.services/claim-account?productId=GeoCoding&locale=en_US&token=" + claimTokenID + "\n");
        driver.get("https://login-qa.saas.precisely.services/claim-account?productId=GeoCoding&locale=en_US&token=" + claimTokenID);
        ip.isElementClickableByXpath(driver,"//input[@id='newPassword']", 60);
        ip.isElementClickableByXpath(driver,"//input[@id='confirmPassword']", 60);
        ip.isElementClickableByXpath(driver,"//button[@id='claimAccountButton']", 60);
        ip.isTextPresentByXPATH(driver, "//h2", "Welcome, "+ signUpFreeUSUserFirstName + " " + signUpFreeUSUserSecondName);
        ip.isTextPresentByXPATH(driver, "//div/div/div", "Your Precisely username is");
        ip.isTextPresentByXPATH(driver, "//div[2]", signUpFreeUSUserID);
        ip.isTextPresentByXPATH(driver, "//div[3]", "Create your password below");
        ip.isTextPresentByXPATH(driver, "//p", "Your password must include at least:");
        ip.isTextPresentByXPATH(driver, "//li[1]/span", "8 characters");
        ip.isTextPresentByXPATH(driver, "//li[2]/span", "1 uppercase letter");
        ip.isTextPresentByXPATH(driver, "//li[3]/span", "1 digit or special character");
        driver.findElement(By.xpath("//input[@id='newPassword']")).sendKeys("Pitney@123");
        driver.findElement(By.xpath("//input[@id='confirmPassword']")).sendKeys("Pitney@123");
        driver.findElement(By.xpath("//button[@id='claimAccountButton']")).click();
        ip.isElementClickableByXpath(driver, xpv.getTokenValue("signInUserName"), 60);
    }    
}
