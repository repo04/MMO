package com.mmo.pages;

import com.mmo.util.BaseClass;
import org.openqa.selenium.By;
import org.testng.Assert;

public class BillingPage extends BaseClass {

    SignUpPage sp = new SignUpPage();

    public void upgradePlan(String userID, String userFirstName, String userSecondName, String plan) {
        ip.isTextPresentByXPATH(driver, "//h1", "Billing & Plans");
        ip.isTextPresentByXPATH(driver, "//h2", "Plan Subscription");
        driver.findElement(By.xpath("//div[3]/button")).click();
        ip.isTextPresentByXPATH(driver, "//ngb-modal-window/div/div/div/h1", "MapMarker pricing plans");
        ip.isTextPresentByXPATH(driver, "//div[2]/div/div[2]/h2", "Pay as you go 5k");

        switch(plan){
            case "5k":
                ip.isElementClickableByXpath(driver, "//a[contains(@href, '/upgrade?plan=gc_5k_monthly')]", 60);
                driver.findElement(By.xpath("//a[contains(@href, '/upgrade?plan=gc_5k_monthly')]")).click();
                break;
            case "Prof":
                ip.isElementClickableByXpath(driver, "//a[contains(@href, '/upgrade?plan=gc_300k_quarterly')]", 60);
                driver.findElement(By.xpath("//a[contains(@href, '/upgrade?plan=gc_300k_quarterly')]")).click();
        }

        ip.isElementClickableByXpath(driver, "//div[@id='paymentinfoheading']", 60);
        ip.isTextPresentByXPATH(driver, "//div[@id='paymentinfoheading']", "Payment Info");
        ip.isURLContains(driver, "upgrade");
        sp.enterPaymentDetails();
        ip.isURLContains(driver, "/dashboard");
        ip.isTextPresentByXPATH(driver, "//a/div/div", userFirstName + " " + userSecondName);
        String text = driver.findElement(By.xpath("//div[2]/div/div")).getText();
        //text.substring(text.indexOf("/") + 2, text.lastIndexOf("geocodes") - 1)
        switch(plan){
            case "5k":
                Assert.assertEquals(text,"0 successful geocodes / 5,000 geocodes", "Geocodes not matched");
                break;
            case "Prof":
                Assert.assertEquals(text,"0 successful geocodes / 300,000 geocodes", "Geocodes not matched");
        }
    }
}
