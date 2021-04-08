package com.mmo.pages;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class BillingPage extends BaseClass {

    SignUpPage sp = new SignUpPage();
    private Actions a = new Actions();

    public void changePlan(String userID, String userFirstName, String userSecondName, String plan) {
        ip.isGetTextContainsByXPATH(driver, "//h1", "Billing & Plans");
        ip.isGetTextContainsByXPATH(driver, "//h2", "Plan Subscription");
        ip.isElementClickableByXpath(driver, "//button[contains(text(),'Upgrade Plan')]", 60);
        driver.findElement(By.xpath("//button[contains(text(),'Upgrade Plan')]")).click();
        ip.isGetTextContainsByXPATH(driver, "//ngb-modal-window/div/div/div/h1", "MapMarker pricing plans");

        switch(plan){
            case "Free":
                ip.isElementClickableByXpath(driver, "//a[contains(text(),'Choose')]", 60);
                driver.findElement(By.xpath("//a[contains(text(),'Choose')]")).click();
                ip.isGetTextContainsByXPATH(driver, "//h5", "Confirm Plan Downgrade");
                ip.isGetTextContainsByXPATH(driver, "//p", "Are you sure you want to downgrade your plan to Free Trial?");
                ip.isElementClickableByXpath(driver, "//a[contains(@href, '/upgrade?plan=free')]", 60);
                driver.findElement(By.xpath("//a[contains(@href, '/upgrade?plan=free')]")).click();
                break;
            case "5k":
                ip.isElementClickableByXpath(driver, "//a[contains(@href, '/upgrade?plan=gc_5k_monthly')]", 60);
                driver.findElement(By.xpath("//a[contains(@href, '/upgrade?plan=gc_5k_monthly')]")).click();
                break;
            case "Prof":
                ip.isElementClickableByXpath(driver, "//a[contains(@href, '/upgrade?plan=gc_300k_quarterly')]", 60);
                driver.findElement(By.xpath("//a[contains(@href, '/upgrade?plan=gc_300k_quarterly')]")).click();
        }

        if(!plan.contentEquals("Free")){
            ip.isElementClickableByXpath(driver, "//div[@id='paymentinfoheading']", 60);
            ip.isGetTextContainsByXPATH(driver, "//div[@id='paymentinfoheading']", "Enter your payment information.");
            sp.enterPaymentDetails(plan);
        }else{
            ip.isURLContains(driver, "?plan=free");
            ip.isURLContains(driver, "thanks/geocoding");
            ip.isGetTextContainsByXPATH(driver, "//p[@id='successtext']", "Success!");
            ip.isGetTextContainsByXPATH(driver, "//p[@id='youraccttext']", "Your subscription has been successfully updated. Redirecting to dashboard.");
        }

        ip.isURLContains(driver, "/dashboard");
        u.waitTillSpinnerDisable(driver, "//div[starts-with(@class,'spinner-sample')]");
        ip.isGetTextContainsByXPATH(driver, "//a/div/div", userFirstName + " " + userSecondName);
        ip.isElementPresentByCSS(driver, "div.progressContainerHead");
        String text = driver.findElement(By.cssSelector("div.progressContainerHead")).getText();

        switch(plan){
            case "Free":
                Assert.assertEquals(text,"0 successful geocodes / 2,500 geocodes", "Geocodes not matched");
                a.navigateToBillingPlan();
                ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div/div/h2", "Free Trial");
                ip.isGetTextContainsByXPATH(driver, "//h4[2]", "2,500 geocodes");
//                ip.isGetTextContainsByXPATH(driver, "//tr[2]/td[4]", "0");
//                ip.isGetTextContainsByXPATH(driver, "//tr[2]/td[5]", "0");
//                ip.isElementClickableByXpath(driver, "(//a[contains(text(),'View')])[2]", 60);
                break;
            case "5k":
                Assert.assertEquals(text,"0 successful geocodes / 5,000 geocodes", "Geocodes not matched");
                a.navigateToBillingPlan();
                ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div/div/h2", "Pay as you go 5k");
                ip.isGetTextContainsByXPATH(driver, "//h4[2]", "5,000 geocodes");
//                ip.isGetTextContainsByXPATH(driver, "//tr[2]/td[4]", "50");
//                ip.isGetTextContainsByXPATH(driver, "//tr[2]/td[5]", "50");
//                ip.isElementClickableByXpath(driver, "(//a[contains(text(),'View')])[2]", 60);
                break;
            case "Prof":
                Assert.assertEquals(text,"0 successful geocodes / 300,000 geocodes", "Geocodes not matched");
                a.navigateToBillingPlan();
                ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div/div/h2", "Professional");
                ip.isGetTextContainsByXPATH(driver, "//h4[2]", "300,000 geocodes");
//                ip.isGetTextContainsByXPATH(driver, "//tr[2]/td[4]", "2700");
//                ip.isGetTextContainsByXPATH(driver, "//tr[2]/td[5]", "2700");
//                ip.isElementClickableByXpath(driver, "(//a[contains(text(),'View')])[2]", 60);
        }
    }

    public void verifyBillingPage(String userSecondName) {

        ip.isGetTextContainsByXPATH(driver, "//h1", "Billing & Plans");
        ip.isGetTextContainsByXPATH(driver, "//h2", "Plan Subscription");
        ip.isGetTextContainsByXPATH(driver, "//div[2]/h2", "Billing History");
        ip.isGetTextContainsByXPATH(driver, "//th[1]", "Invoice Number");
        ip.isGetTextContainsByXPATH(driver, "//th[2]", "Bill Date");
        ip.isGetTextContainsByXPATH(driver, "//th[3]", "Paid Date");
        ip.isGetTextContainsByXPATH(driver, "//th[4]", "Bill Amount");
        ip.isGetTextContainsByXPATH(driver, "//th[5]", "Paid Amount");
        ip.isGetTextContainsByXPATH(driver, "//h4", "0 successful geocodes");
        ip.isGetTextContainsByXPATH(driver, "//div/div/div/div[2]/div", "out of");

        if(userSecondName.substring(0,4).contentEquals("Prof") || userSecondName.substring(0,2).contentEquals("5k")){
            ip.isElementPresentByXPATH(driver, "//button[contains(text(),'Upgrade Plan')]");
            ip.isGetTextContainsByXPATH(driver, "//div[4]/div/div/h2", "Payment Information");
            Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[1]/div/div/input")).
                    getAttribute("ng-reflect-model"),"CARD NAME NEW", "Card Name does not match");
            Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[2]/div[1]/div/div/input")).
                    getAttribute("ng-reflect-model"),"**** **** **** 0505", "Card Number does not match");
            Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[2]/div[2]/div/input")).
                    getAttribute("ng-reflect-model"),"11/2024", "Exp date does not match");
            Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[3]/div/div/input")).
                    getAttribute("ng-reflect-model"),"1 Global Vw", "Street does not match");
            Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[5]/div[1]/div/input")).
                    getAttribute("ng-reflect-model"),"12180-8371", "PostCode does not match");
            Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[5]/div[2]/div/input")).
                    getAttribute("ng-reflect-model"),"Troy", "City does not match");
            Assert.assertEquals(driver.findElement(By.xpath("//select[@class='form-control']/option")).getText(),
                    "New York", "State does not match");
        }

        if(userSecondName.contains("FreeNonUS")){
            ip.invisibilityOfElementByXpath(driver, "//button[contains(text(),'Upgrade Plan')]");
        }

        switch(userSecondName.substring(0,4)){
            case "Free":
                ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div/div/h2", "Free Trial");
                ip.isGetTextContainsByXPATH(driver, "//h4[2]", "2,500 geocodes");
                ip.isGetTextContainsByXPATH(driver, "//td", "No Invoices");
                break;
            case "Prof":
                ip.isElementClickableByXpath(driver, "//a[contains(text(),'View')]", 60);
                ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div[1]", "Professional");
                ip.isGetTextContainsByXPATH(driver, "//h4[2]", "300,000 geocodes");

                ip.isGetTextContainsByXPATH(driver, "//tr[1]/td[4]", "2700");
                ip.isGetTextContainsByXPATH(driver, "//tr[1]/td[5]", "2700");

                driver.findElement(By.xpath("//a[contains(text(),'View')]")).click();

                ip.isGetTextContainsByXPATH(driver, "//h2[@id='modal-basic-title']", "Invoice");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr/td/h1",
                        "Your Pitney Bowes Software Statement");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table/tbody/tr/td",
                        "$ 0.00");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td",
                        "GC 300K Quarterly (Commitment)");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td[2]",
                        "2,700.00");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr[2]/td[2]",
                        "-2,700.00");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr[3]/td/strong",
                        "$ 0.00");
                break;
            default :
                ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div/div/h2", "Pay as you go 5k");
                ip.isGetTextContainsByXPATH(driver, "//h4[2]", "5,000 geocodes");
                ip.isGetTextContainsByXPATH(driver, "//tr[1]/td[4]", "$54.00");
                ip.isGetTextContainsByXPATH(driver, "//tr[1]/td[5]", "$54.00");

                /*driver.findElement(By.xpath("//td[6]/button")).click();

                ip.isGetTextContainsByXPATH(driver, "//h2[@id='modal-basic-title']", "Invoice");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr/td/h1",
                        "Your Pitney Bowes Software Statement");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table/tbody/tr/td",
                        "$ 0.00");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td",
                        "GC 5K Monthly (Recurring Plan Charge)");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td[2]",
                        "50.00");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr[2]/td[2]",
                        "-50");
                ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr[3]/td/strong",
                        "$ 0.00");*/
        }

//        driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
//        ip.isElementClickableByXpath(driver, "//a[contains(text(),'View')]", 60);
    }

    public void updateCard() {
        ip.isElementClickableByXpath(driver, "//button[contains(.,'Update Card')]", 60);
        driver.findElement(By.xpath("//button[contains(.,'Update Card')]")).click();
        ip.isElementClickableByXpath(driver, "//input[@type='text']", 60);
        driver.findElement(By.xpath("//input[@type='text']")).clear();
        driver.findElement(By.xpath("//input[@type='text']")).sendKeys("CARD 2");
        ip.isElementClickableByXpath(driver, "(//input[@type='text'])[2]", 60);
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).sendKeys("NAME 2");
        ip.frameToBeAvailableByXpathAndSwitchToIt(driver,"/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/form/div[2]/div/div/div/div/iframe");
        ip.isElementClickableByXpath(driver, "//input[@id='recurly-hosted-field-input']", 60);
        driver.findElement(By.xpath("//input[@id='recurly-hosted-field-input']")).clear();
        driver.findElement(By.xpath("//input[@id='recurly-hosted-field-input']")).sendKeys("378282246310005");
        driver.findElement(By.xpath("(//input[@type='tel'])[2]")).clear();
        driver.findElement(By.xpath("(//input[@type='tel'])[2]")).sendKeys("01 / 25");
        driver.findElement(By.xpath("(//input[@type='tel'])[3]")).clear();
        driver.findElement(By.xpath("(//input[@type='tel'])[3]")).sendKeys("1234");
        driver.switchTo().parentFrame();
        ip.isElementClickableByXpath(driver, "(//input[@type='text'])[3]", 60);
        driver.findElement(By.xpath("(//input[@type='text'])[3]")).clear();
        driver.findElement(By.xpath("(//input[@type='text'])[3]")).sendKeys("1 Global View");
        driver.findElement(By.xpath("(//input[@type='text'])[5]")).clear();
        driver.findElement(By.xpath("(//input[@type='text'])[5]")).sendKeys("12180");
        driver.findElement(By.xpath("(//input[@type='text'])[6]")).clear();
        driver.findElement(By.xpath("(//input[@type='text'])[6]")).sendKeys("TROY");
        new Select(driver.findElement(By.xpath("//select"))).selectByVisibleText("New York");
        driver.findElement(By.xpath("//button[@id='validateAddressBtn']")).click();
        ip.isGetTextContainsByXPATH(driver, "//td[2]/div","1 Global Vw", 15);
        ip.isGetTextContainsByXPATH(driver, "//td[2]/div[3]","Troy, New York", 15);
        ip.isGetTextContainsByXPATH(driver, "//td[2]/div[4]","12180-8371", 15);
        driver.findElement(By.xpath("//input[@name='confirmAddrRadio']")).click();
        driver.findElement(By.xpath("//button[@id='submitPaymentBtn']")).click();
        ip.isGetTextContainsByXPATH(driver, "//div[4]/div[2]/div/div/div",
                "Payment information updated successfully.", 15);

        ip.isElementPresentByXPATH(driver, "/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[1]/div/div/input");
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[1]/div/div/input")).
                getAttribute("ng-reflect-model"),"CARD 2 NAME 2", "Card Name does not match");
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[2]/div[1]/div/div/input")).
                getAttribute("ng-reflect-model"),"**** **** **** 0005", "Card Number does not match");
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[2]/div[2]/div/input")).
                getAttribute("ng-reflect-model"),"1/2025", "Exp date does not match");
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[3]/div/div/input")).
                getAttribute("ng-reflect-model"),"1 Global Vw", "Street does not match");
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[5]/div[1]/div/input")).
                getAttribute("ng-reflect-model"),"12180-8371", "PostCode does not match");
        Assert.assertEquals(driver.findElement(By.xpath("/html/body/app-root/div/billing/div[1]/div/div[4]/div[2]/div[5]/div[2]/div/input")).
                getAttribute("ng-reflect-model"),"Troy", "City does not match");
        Assert.assertEquals(driver.findElement(By.xpath("//select[@class='form-control']/option")).getText(),
                "New York", "State does not match");
    }
}