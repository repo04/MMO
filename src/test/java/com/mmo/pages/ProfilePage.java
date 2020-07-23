package com.mmo.pages;

import com.mmo.util.BaseClass;
import org.openqa.selenium.By;
import org.testng.Assert;

public class ProfilePage extends BaseClass {

    /**
     *
     * @param userID
     * @param userFirstName
     * @param userSecondName
     */
    public void verifyProfilePage(String userID, String userFirstName, String userSecondName) {
        ip.isElementPresentByXPATH(driver, "//input[@id='firstName']");
        Assert.assertEquals(driver.findElement(By.xpath("//input[@id='firstName']")).getAttribute("ng-reflect-model"), userFirstName, "User's First Name does not match");
        Assert.assertEquals(driver.findElement(By.xpath("//input[@id='lastName']")).getAttribute("ng-reflect-model"), userSecondName, "User's Second Name does not match");
//        Assert.assertEquals(driver.findElement(By.xpath("//input[@id='emailId']")).getAttribute("ng-reflect-model"), userID, "User's Email ID does not match");
        Assert.assertEquals(driver.findElement(By.xpath("//input[@id='companyName']")).getAttribute("ng-reflect-model"),"PRECISELY", "User's Company does not match");
    }
}
