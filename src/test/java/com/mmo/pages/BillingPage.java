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
        ip.isTextPresentByXPATH(driver, "//h1", "Billing & Plans");
        ip.isTextPresentByXPATH(driver, "//h2", "Plan Subscription");
        ip.isElementClickableByXpath(driver, "//div[3]/button", 60);
        driver.findElement(By.xpath("//div[3]/button")).click();
        ip.isTextPresentByXPATH(driver, "//ngb-modal-window/div/div/div/h1", "MapMarker pricing plans");

        switch(plan){
            case "Free":
                ip.isElementClickableByXpath(driver, "//a[contains(text(),'Choose')]", 60);
                driver.findElement(By.xpath("//a[contains(text(),'Choose')]")).click();
                ip.isTextPresentByXPATH(driver, "//h5", "Confirm Plan Downgrade");
                ip.isTextPresentByXPATH(driver, "//p", "Are you sure you want to downgrade your plan to Free Trial?");
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
            ip.isTextPresentByXPATH(driver, "//div[@id='paymentinfoheading']", "Payment Info");
            ip.isURLContains(driver, "upgrade");
            sp.enterPaymentDetails();
        }else{
            ip.isURLContains(driver, "?plan=free");
            ip.isURLContains(driver, "thanks/geocoding");
            ip.isTextPresentByXPATH(driver, "//p[@id='successtext']", "Success!");
            ip.isTextPresentByXPATH(driver, "//p[@id='youraccttext']", "Your subscription has been successfully updated. Redirecting to dashboard.");
        }

        ip.isURLContains(driver, "/dashboard");
        u.waitTillSpinnerDisable(driver, "//div[starts-with(@class,'spinner-sample')]");
        ip.isTextPresentByXPATH(driver, "//a/div/div", userFirstName + " " + userSecondName);
        String text = driver.findElement(By.xpath("//div[2]/div/div")).getText();

        switch(plan){
            case "Free":
                Assert.assertEquals(text,"0 successful geocodes / 2,500 geocodes", "Geocodes not matched");
                a.navigateToBillingPlan();
                ip.isTextPresentByXPATH(driver, "//div[2]/div/div/div/div/h2", "Free Trial");
                ip.isTextPresentByXPATH(driver, "//h4[2]", "2,500 geocodes");
                ip.isTextPresentByXPATH(driver, "//tr[2]/td[4]", "0");
                ip.isTextPresentByXPATH(driver, "//tr[2]/td[5]", "0");
                ip.isElementClickableByXpath(driver, "(//a[contains(text(),'View')])[2]", 60);
                break;
            case "5k":
                Assert.assertEquals(text,"0 successful geocodes / 5,000 geocodes", "Geocodes not matched");
                a.navigateToBillingPlan();
                ip.isTextPresentByXPATH(driver, "//div[2]/div/div/div/div/h2", "Pay as you go 5k");
                ip.isTextPresentByXPATH(driver, "//h4[2]", "5,000 geocodes");
                ip.isTextPresentByXPATH(driver, "//tr[2]/td[4]", "50");
                ip.isTextPresentByXPATH(driver, "//tr[2]/td[5]", "50");
                ip.isElementClickableByXpath(driver, "(//a[contains(text(),'View')])[2]", 60);
                break;
            case "Prof":
                Assert.assertEquals(text,"0 successful geocodes / 300,000 geocodes", "Geocodes not matched");
                a.navigateToBillingPlan();
                ip.isTextPresentByXPATH(driver, "//div[2]/div/div/div/div/h2", "Professional");
                ip.isTextPresentByXPATH(driver, "//h4[2]", "300,000 geocodes");
                ip.isTextPresentByXPATH(driver, "//tr[2]/td[4]", "2700");
                ip.isTextPresentByXPATH(driver, "//tr[2]/td[5]", "2700");
                ip.isElementClickableByXpath(driver, "(//a[contains(text(),'View')])[2]", 60);
        }
    }

    public void verifyBillingPage(String userSecondName) {

        ip.isTextPresentByXPATH(driver, "//div[2]/h2", "Billing History");
        ip.isTextPresentByXPATH(driver, "//th[4]", "Bill Amount");
        ip.isTextPresentByXPATH(driver, "//th[5]", "Paid Amount");

        if(userSecondName.substring(0,4).contentEquals("Prof") || userSecondName.substring(0,2).contentEquals("5k")){
            ip.isTextPresentByXPATH(driver, "//div[3]/h2", "Billing Info");
            ip.isTextPresentByXPATH(driver, "//label", "Name on the card");
            Assert.assertEquals(driver.findElement(By.xpath("//div[@class='container fullview']/div/div/div/div[1]/div[1]/div[1]/input[1]")).getAttribute("ng-reflect-model"),"CARD NAME", "Card Name does not match");
            Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inner-addon right-addon']/input[@class='form-control ng-untouched ng-pristine']")).getAttribute("ng-reflect-model"),"**** **** **** 1111", "Card Number does not match");
            Assert.assertEquals(driver.findElement(By.xpath("//div[@class='row']/div[2]/div[1]/input[1]")).getAttribute("ng-reflect-model"),
                    "1/2021", "Exp date does not match");
            Assert.assertEquals(driver.findElement(By.xpath("//body[@class='bg-white']//div[@class='container fullview']//div//div//div[3]//div[1]//div[1]//input[1]")).getAttribute("ng-reflect-model"),"DERRY STREETS", "Street does not match");
            Assert.assertEquals(driver.findElement(By.xpath("//div[@class='col-md-3']//input[@class='form-control ng-untouched ng-pristine']")).getAttribute("ng-reflect-model"),"DERRY", "City does not match");
            Assert.assertEquals(driver.findElement(By.xpath("//div[@class='row']//div[3]//div[1]//input[1]")).getAttribute("ng-reflect-model"),
                    "03038", "PostCode does not match");
            Assert.assertEquals(driver.findElement(By.xpath("//select[@class='form-control']/option")).getText(),
                    "NH", "State does not match");
        }

        switch(userSecondName.substring(0,4)){
            case "Free":
                ip.isTextPresentByXPATH(driver, "//div[2]/div/div/div/div/h2", "Free Trial");
                ip.isTextPresentByXPATH(driver, "//h4[2]", "2,500 geocodes");
                ip.isTextPresentByXPATH(driver, "//tr[1]/td[4]", "0");
                ip.isTextPresentByXPATH(driver, "//tr[1]/td[5]", "0");
                ip.isElementClickableByXpath(driver, "//a[contains(text(),'View')]", 60);

                driver.findElement(By.xpath("//a[contains(text(),'View')]")).click();

                ip.isTextPresentByXPATH(driver, "//h2[@id='modal-basic-title']", "Invoice");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr/td/h1",
                        "Your Pitney Bowes Software Statement");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table/tbody/tr/td",
                        "$ 0.00");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td",
                        "GC Free Trial (Recurring Plan Charge)");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td[2]",
                        "0.00");
                break;
            case "Prof":
                ip.isTextPresentByXPATH(driver, "//div[2]/div/div/div/div/h2", "Professional");
                ip.isTextPresentByXPATH(driver, "//h4[2]", "300,000 geocodes");
                ip.isTextPresentByXPATH(driver, "//tr[1]/td[4]", "2700");
                ip.isTextPresentByXPATH(driver, "//tr[1]/td[5]", "2700");
                ip.isElementClickableByXpath(driver, "//a[contains(text(),'View')]", 60);

                driver.findElement(By.xpath("//a[contains(text(),'View')]")).click();

                ip.isTextPresentByXPATH(driver, "//h2[@id='modal-basic-title']", "Invoice");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr/td/h1",
                        "Your Pitney Bowes Software Statement");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table/tbody/tr/td",
                        "$ 0.00");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td",
                        "GC 300K Quarterly (Commitment)");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td[2]",
                        "2,700.00");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr[2]/td[2]",
                        "-2,700.00");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr[3]/td/strong",
                        "$ 0.00");
                break;
            default :
                ip.isTextPresentByXPATH(driver, "//div[2]/div/div/div/div/h2", "Pay as you go 5k");
                ip.isTextPresentByXPATH(driver, "//h4[2]", "5,000 geocodes");
                ip.isTextPresentByXPATH(driver, "//div[3]/h2", "Billing Info");
                ip.isTextPresentByXPATH(driver, "//label", "Name on the card");
                ip.isTextPresentByXPATH(driver, "//tr[1]/td[4]", "50");
                ip.isTextPresentByXPATH(driver, "//tr[1]/td[5]", "50");
                ip.isElementClickableByXpath(driver, "//a[contains(text(),'View')]", 60);

                driver.findElement(By.xpath("//a[contains(text(),'View')]")).click();

                ip.isTextPresentByXPATH(driver, "//h2[@id='modal-basic-title']", "Invoice");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr/td/h1",
                        "Your Pitney Bowes Software Statement");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table/tbody/tr/td",
                        "$ 0.00");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td",
                        "GC 5K Monthly (Recurring Plan Charge)");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td[2]",
                        "50.00");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr[2]/td[2]",
                        "-50");
                ip.isTextPresentByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr[3]/td/strong",
                        "$ 0.00");
        }

        driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
        ip.isElementClickableByXpath(driver, "//a[contains(text(),'View')]", 60);
    }

    public void updateCard() {
        ip.isElementClickableByXpath(driver, "//button[contains(.,'Update Card')]", 60);
        driver.findElement(By.xpath("//button[contains(.,'Update Card')]")).click();
        ip.frameToBeAvailableAndSwitchToIt(driver, "paymetricsForm");
        ip.isElementClickableByXpath(driver, "//input[@id='c-cardname']", 60);
        driver.findElement(By.xpath("//input[@id='c-cardname']")).clear();
        String newCardName = "CARD NAME " + u.currentDateTime();
        driver.findElement(By.xpath("//input[@id='c-cardname']")).sendKeys(newCardName);
        driver.findElement(By.xpath("//input[@id='c-cardnumber']")).clear();
        driver.findElement(By.xpath("//input[@id='c-cardnumber']")).sendKeys("4111111111111111");
        driver.findElement(By.xpath("//input[@id='c-cvv']")).clear();
        driver.findElement(By.xpath("//input[@id='c-cvv']")).sendKeys("123");
        new Select(driver.findElement(By.xpath("//select[@id='c-ct']"))).selectByVisibleText("VISA");
        new Select(driver.findElement(By.xpath("//select[@id='c-exmth']"))).selectByVisibleText("Jan");
        new Select(driver.findElement(By.xpath("//select[@id='c-exyr']"))).selectByVisibleText("2021");
        driver.findElement(By.xpath("//input[@id='address1']")).clear();
        driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("DERRY STREETS");
        driver.findElement(By.xpath("//input[@id='city']")).clear();
        driver.findElement(By.xpath("//input[@id='city']")).sendKeys("DERRY");
        new Select(driver.findElement(By.xpath("//select[@id='us-state']"))).selectByVisibleText("NH");
        driver.findElement(By.xpath("//input[@id='postalCode']")).clear();
        driver.findElement(By.xpath("//input[@id='postalCode']")).sendKeys("03038");
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        u.waitTillSpinnerDisable(driver, "//div[starts-with(@class,'spinner-sample')]");
        ip.isElementPresentByXPATH(driver, "//div[@class='container fullview']/div/div/div/div[1]/div[1]/div[1]/input[1]");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='container fullview']/div/div/div/div[1]/div[1]/div[1]/input[1]")).getAttribute("ng-reflect-model"), newCardName, "Card Name does not match");
    }
}
