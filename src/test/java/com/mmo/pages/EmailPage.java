package com.mmo.pages;

import org.openqa.selenium.By;

import com.mmo.util.BaseClass;

import java.util.concurrent.TimeUnit;

public class EmailPage extends BaseClass {

    public void completeRegistration(String signUpFreeUSUserID, String signUpFreeUSUserFirstName, String signUpFreeUSUserSecondName, String claimTokenID) {
        String check = "=";
        if(claimTokenID.contains(check))
        {
            claimTokenID = claimTokenID.replaceAll(check, "");
        }
        if(signUpFreeUSUserID.contains("geoTax")){
            System.out.println("CLAIM ID: https://" + loginURL + "/claim-account?productId=geotax&locale=en_US&token=" + claimTokenID + "\n");
            driver.get("https://" + loginURL + "/claim-account?productId=geotax&locale=en_US&token=" + claimTokenID);
        }else{
            System.out.println("CLAIM ID: https://" + loginURL + "/claim-account?productId=GeoCoding&locale=en_US&token=" + claimTokenID + "\n");
            driver.get("https://" + loginURL + "/claim-account?productId=GeoCoding&locale=en_US&token=" + claimTokenID);
        }
        ip.isElementClickableByXpath(driver,"//input[@id='newPassword']", 60);
        ip.isElementClickableByXpath(driver,"//input[@id='confirmPassword']", 60);
        ip.isElementClickableByXpath(driver,"//button[@id='claimAccountButton']", 60);
        ip.isGetTextContainsByXPATH(driver, "//h2", "Welcome, "+ signUpFreeUSUserFirstName + " " + signUpFreeUSUserSecondName);
        ip.isGetTextContainsByXPATH(driver, "//div/div/div", "Your Precisely username is");
        ip.isGetTextContainsByXPATH(driver, "//div[2]", signUpFreeUSUserID);
        ip.isGetTextContainsByXPATH(driver, "//div[3]", "Create your password below");
        ip.isGetTextContainsByXPATH(driver, "//p", "Your password must include at least:");
        ip.isGetTextContainsByXPATH(driver, "//li[1]/span", "8 characters");
        ip.isGetTextContainsByXPATH(driver, "//li[2]/span", "1 uppercase letter");
        ip.isGetTextContainsByXPATH(driver, "//li[3]/span", "1 digit or special character");
        driver.findElement(By.xpath("//input[@id='newPassword']")).sendKeys("Precisely@123");
        driver.findElement(By.xpath("//input[@id='confirmPassword']")).sendKeys("Precisely@123");
        driver.findElement(By.xpath("//button[@id='claimAccountButton']")).click();
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        ip.isElementClickableByXpath(driver, xpv.getTokenValue("signInUserName"), 60);
    }
}
