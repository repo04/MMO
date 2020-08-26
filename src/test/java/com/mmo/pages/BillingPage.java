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
            ip.isGetTextContainsByXPATH(driver, "//div[@id='paymentinfoheading']", "Payment Info");
            ip.isURLContains(driver, "upgrade");
            sp.enterPaymentDetails();
        }else{
            ip.isURLContains(driver, "?plan=free");
            ip.isURLContains(driver, "thanks/geocoding");
            ip.isGetTextContainsByXPATH(driver, "//p[@id='successtext']", "Success!");
            ip.isGetTextContainsByXPATH(driver, "//p[@id='youraccttext']", "Your subscription has been successfully updated. Redirecting to dashboard.");
        }

        ip.isURLContains(driver, "/dashboard");
        u.waitTillSpinnerDisable(driver, "//div[starts-with(@class,'spinner-sample')]");
        ip.isGetTextContainsByXPATH(driver, "//a/div/div", userFirstName + " " + userSecondName);
        String text = driver.findElement(By.xpath("//div[2]/div/div")).getText();

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

        ip.isGetTextContainsByXPATH(driver, "//div[2]/h2", "Billing History");
        ip.isGetTextContainsByXPATH(driver, "//th[4]", "Bill Amount");
        ip.isGetTextContainsByXPATH(driver, "//th[5]", "Paid Amount");

        if(userSecondName.substring(0,4).contentEquals("Prof") || userSecondName.substring(0,2).contentEquals("5k")){
            ip.isElementPresentByXPATH(driver, "//button[contains(text(),'Upgrade Plan')]");
            ip.isGetTextContainsByXPATH(driver, "//div[3]/h2", "Billing Info");
            ip.isGetTextContainsByXPATH(driver, "//label", "Name on the card");
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

        if(userSecondName.contains("FreeNonUS")){
            ip.invisibilityOfElementByXpath(driver, "//button[contains(text(),'Upgrade Plan')]");
        }

        switch(userSecondName.substring(0,4)){
            case "Free":
                ip.isElementClickableByXpath(driver, "//a[contains(text(),'View')]", 60);
                ip.invisibilityOfElementByXpathWithText(driver, "//div[3]/h2", "Billing Info");
                ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div[1]", "Free Trial");
                //ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div[2]", "0 successful geocodes" + "\n" + "out of" +"\n" + "2,500 geocodes");
                ip.isGetTextContainsByXPATH(driver, "//h4", "0");
                ip.isGetTextContainsByXPATH(driver, "//div/div/div/div[2]/div", "out of");
                ip.isGetTextContainsByXPATH(driver, "//h4[2]", "2,500 geocodes");
                ip.isGetTextContainsByXPATH(driver, "//tr[1]/td[4]", "0");
                ip.isGetTextContainsByXPATH(driver, "//tr[1]/td[5]", "0");

                driver.findElement(By.xpath("//a[contains(text(),'View')]")).click();

                ip.isGetTextContainsByXPATH(driver, "//h2[@id='modal-basic-title']", "Invoice");
                if(!userSecondName.contains("NonUS")){
                    ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr/td/h1",
                            "Your Pitney Bowes Software Statement");
                    ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table/tbody/tr/td",
                            "$ 0.00");
                    ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td",
                            "GC Free Trial (Recurring Plan Charge)");
                    ip.isGetTextContainsByXPATH(driver, "//div[@id='content']/table[2]/tbody/tr[2]/td/table[2]/tbody/tr/td[2]",
                            "0.00");
                }
                break;
            case "Prof":
                ip.isElementClickableByXpath(driver, "//a[contains(text(),'View')]", 60);
                ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div[1]", "Professional");
 //               ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div[2]", "0 successful geocodes" + "\n" + "out of" +"\n" + "300,000 geocodes");
                ip.isGetTextContainsByXPATH(driver, "//h4", "0");
                ip.isGetTextContainsByXPATH(driver, "//div/div/div/div[2]/div", "out of");
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
                ip.isElementClickableByXpath(driver, "//a[contains(text(),'View')]", 60);
                ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div[1]", "Pay as you go 5k");
                //ip.isGetTextContainsByXPATH(driver, "//div[2]/div/div/div[2]", "0 successful geocodes" + "\n" + "out of" +"\n" + "5,000 geocodes");
                ip.isGetTextContainsByXPATH(driver, "//h4", "0");
                ip.isGetTextContainsByXPATH(driver, "//div/div/div/div[2]/div", "out of");
                ip.isGetTextContainsByXPATH(driver, "//h4[2]", "5,000 geocodes");
                ip.isGetTextContainsByXPATH(driver, "//div[3]/h2", "Billing Info");
                ip.isGetTextContainsByXPATH(driver, "//label", "Name on the card");
                ip.isGetTextContainsByXPATH(driver, "//tr[1]/td[4]", "50");
                ip.isGetTextContainsByXPATH(driver, "//tr[1]/td[5]", "50");

                driver.findElement(By.xpath("//a[contains(text(),'View')]")).click();

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
        u.clickByJavaScript(driver, "//button[@type='submit']");
        u.waitTillSpinnerDisable(driver, "//div[starts-with(@class,'spinner-sample')]");
        ip.isElementPresentByXPATH(driver, "//div[@class='container fullview']/div/div/div/div[1]/div[1]/div[1]/input[1]");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='container fullview']/div/div/div/div[1]/div[1]/div[1]/input[1]")).getAttribute("ng-reflect-model"), newCardName, "Card Name does not match");
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
}