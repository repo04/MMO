package com.mmo.pages;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;

public class SignUpPage extends BaseClass{

	private static String signUpDetails[] = new String[3];
	private String userEmailId;
	private String userFirstName;
	private String userSecondName;
	private String dateAndTime;
	public Actions a = new Actions();
	
	public void signUpUser(String plan, String region) {

		this.dateAndTime = u.currentDateTime();
		switch (plan) {
		case "free":
			ip.isElementPresentByXPATH(driver, "//a[@onclick=\"selectPlan('?plan=free')\"]");
			driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=free')\"]")).click();
			ip.isURLContains(driver, "signup/geocoding?plan=free");
			this.userFirstName = "autoMMOPb";
			this.userSecondName = "FreeUS" + this.dateAndTime;
			break;
		case "5k":
			ip.isElementPresentByXPATH(driver, "//a[@onclick=\"selectPlan('?plan=gc_5k_monthly')\"]");
			driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=gc_5k_monthly')\"]")).click();
			ip.isURLContains(driver, "signup/geocoding?plan=gc_5k_monthly");
			this.userFirstName = "autoMMOPb";
			this.userSecondName = "5k" + this.dateAndTime;
			break;
		case "prof":
			ip.isElementPresentByXPATH(driver, "//a[@onclick=\"selectPlan('?plan=starter_100k_monthly')\"]");
			driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=starter_100k_monthly')\"]")).click();
			ip.isURLContains(driver, "signup/geocoding?plan=starter_100k_monthly");
			this.userFirstName = "autoMMOPb";
			this.userSecondName = "Prof" + this.dateAndTime;
		}

		//Terms of Use
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'Terms of Use')]", 60);
		driver.findElement(By.xpath("//a[contains(text(),'Terms of Use')]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Terms of Use | Pitney Bowes", ip);
		//Privacy
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'Privacy')]", 60);
		driver.findElement(By.xpath("//a[contains(text(),'Privacy')]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Privacy Statement | Pitney Bowes", ip);
		
		if(!region.equalsIgnoreCase("US")) {
			this.userFirstName = "autoMMOPb";	
			this.userSecondName = "FreeNonUS" + this.dateAndTime;
		}else {
			ip.isElementClickableByXpath(driver, "//select[@id='regionChangeDropdown']", 60);
			ip.isElementClickableByXpath(driver, "//select[@id='localeChangeDropdown']", 60);
			new Select(driver.findElement(By.xpath("//select[@id='regionChangeDropdown']"))).selectByVisibleText("North, Central and South America");
			new Select(driver.findElement(By.xpath("//select[@id='localeChangeDropdown']"))).selectByVisibleText("United States");			
		}

		ip.isElementClickableByXpath(driver, "//input[@id='firstname-nf']", 60);
		driver.findElement(By.xpath("//input[@id='firstname-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='firstname-nf']")).sendKeys(this.userFirstName);
		driver.findElement(By.xpath("//input[@id='lastname-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='lastname-nf']")).sendKeys(this.userSecondName);
		driver.findElement(By.xpath("//input[@id='email-nf']")).clear();
		this.userEmailId = this.userFirstName + "+" + this.userSecondName + "@gmail.com";
		driver.findElement(By.xpath("//input[@id='email-nf']")).sendKeys(this.userEmailId);
		driver.findElement(By.xpath("//input[@id='verifyemail']")).clear();
		driver.findElement(By.xpath("//input[@id='verifyemail']")).sendKeys(this.userEmailId);
		driver.findElement(By.xpath("//input[@id='company-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='company-nf']")).sendKeys("PB");
		driver.findElement(By.xpath("//input[@id='address1']")).clear();
		driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("13 Route 111");
		ip.isElementPresentByXPATH(driver, "//div[@id='geoitem1']");
		assertEquals(driver.findElement(By.xpath("//div[@id='geoitem1']")).getText(), "13 Route 111, Derry, NH 03038");
		driver.findElement(By.xpath("//div[@id='geoitem1']")).click();
		driver.findElement(By.xpath("//input[@id='contactnumber-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='contactnumber-nf']")).sendKeys("1234567890");
		ip.isElementClickableByXpath(driver, "//input[@id='zip']", 60);
		assertEquals(u.getFirstSelectedOptionFromSelect(driver, "//select[@id='stateselectbox']"), "NH - New Hampshire");
		ip.isElementClickableByXpath(driver, "//input[@id='city']", 60);
		assertEquals(driver.findElement(By.xpath("//input[@id='city']")).getAttribute("value"), "Derry");
		assertEquals(driver.findElement(By.xpath("//input[@id='zip']")).getAttribute("value"), "03038");
		System.out.println("**" + this.userFirstName + "**" + this.userSecondName + "**" + this.userEmailId);
		driver.findElement(By.xpath("//button[@id='createaccbtn-nf']")).click();
		if(!plan.equalsIgnoreCase("free")) {
			enterPaymentDetails(plan);				
		}
		ip.isTextPresentByXPATH(driver, "//p[@id='successtext']", "Success!");
		ip.isTextPresentByXPATH(driver, "//p[@id='youraccttext']", "Thank you for registering for MapMarker. "
				+ "Please check your email to complete your registration process. You may safely close this window.");
	}

	/**
	 * 
	 * @param plan
	 */
	public void enterPaymentDetails(String plan){
		ip.isURLContains(driver, "payment/geocoding");
		ip.isTextPresentByXPATH(driver, "//div[@id='paymentinfoheading']", "Payment Info");
		ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Payment')]");
		
		List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
		System.out.println("iframes count:" + iframes.size());
		String iframeID = null;
			for (WebElement frame : iframes) {
				iframeID = frame.getAttribute("id");
				System.out.println("Iframe ID: " + iframeID);
			}
		driver.switchTo().frame(iframeID);
		ip.isElementClickableByXpath(driver, "//input[@id='c-cardname']", 60);
		driver.findElement(By.xpath("//input[@id='c-cardname']")).clear();
		driver.findElement(By.xpath("//input[@id='c-cardname']")).sendKeys("CARD NAME");
		driver.findElement(By.xpath("//input[@id='c-cardnumber']")).clear();
		driver.findElement(By.xpath("//input[@id='c-cardnumber']")).sendKeys("");
		driver.findElement(By.xpath("//input[@id='c-cvv']")).clear();
		driver.findElement(By.xpath("//input[@id='c-cvv']")).sendKeys("");
		new Select(driver.findElement(By.xpath("//select[@id='c-ct']"))).selectByVisibleText("");
		new Select(driver.findElement(By.xpath("//select[@id='c-exmth']"))).selectByVisibleText("Jan");
		driver.findElement(By.xpath("//input[@id='address1']")).clear();
		driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("DERRY STREETS");
		driver.findElement(By.xpath("//input[@id='city']")).clear();
		driver.findElement(By.xpath("//input[@id='city']")).sendKeys("DERRY");
		new Select(driver.findElement(By.xpath("//select[@id='us-state']"))).selectByVisibleText("NH");
		driver.findElement(By.xpath("//input[@id='postalCode']")).clear();
		driver.findElement(By.xpath("//input[@id='postalCode']")).sendKeys("03038");
		new Select(driver.findElement(By.xpath("//select[@id='c-exyr']"))).selectByVisibleText("2021");
		driver.switchTo().defaultContent();
		
		//Terms of Use
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'Terms of Use')]", 60);
		driver.findElement(By.xpath("//a[contains(text(),'Terms of Use')]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Subscription Agreement", ip);
		ip.isElementClickableByXpath(driver, "//button[@id='activateplanbutton']", 60);
		driver.findElement(By.xpath("//button[@id='activateplanbutton']")).click();
	    ip.isURLContains(driver, "thanks/geocoding");
	}
	
	/**
	 * @return userDetails
	 */
	public String[] getUserDetails() {
		signUpDetails[0] =  this.userEmailId;
		signUpDetails[1] =  this.userFirstName;
		signUpDetails[2] =  this.userSecondName;
		return signUpDetails;
	}

}
