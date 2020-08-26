package com.mmo.pages;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;

public class SignUpPage extends BaseClass{

	private static String[][] signUpDetails = new String[1][3];
	private static String userEmailId;
	private static String userFirstName;
	private static String userSecondName;
	private String dateAndTime;
	public Actions a = new Actions();

	public void signUpUser(String plan, String region, String addProductFlow) {

		this.dateAndTime = u.currentDateTime();

		navigateToPlan(plan);

		if(!region.equalsIgnoreCase("US")) {
			this.userFirstName = "mmoAutomated";
			this.userSecondName = "FreeNonUS" + this.dateAndTime;
			ip.isElementClickableByXpath(driver, "//input[@id='zip']", 60);
			ip.isElementClickableByXpath(driver, "//select[@id='regionChangeDropdown']", 60);
			new Select(driver.findElement(By.xpath("//select[@id='regionChangeDropdown']"))).selectByVisibleText("Asia Pacific");
			driver.findElement(By.xpath("//input[@id='zip']")).sendKeys("1234");
			ip.isElementClickableByXpath(driver, "//select[@id='localeChangeDropdown']", 60);
			ip.isElementPresentByXPATH(driver, "//select[@id='localeChangeDropdown']//option[contains(text(),'Australia')]");
			new Select(driver.findElement(By.xpath("//select[@id='localeChangeDropdown']"))).selectByVisibleText("Australia");
			ip.isElementClickableByXpath(driver, "//input[@id='address1']", 60);
			driver.findElement(By.xpath("//input[@id='address1']")).clear();
			driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("1110 TRL");
			ip.isElementPresentByXPATH(driver, "//div[@id='geoitem1']");
			assertEquals(driver.findElement(By.xpath("//div[@id='geoitem1']")).getText(), "1110 TRL, PEMBROOKE, NSW 2446");
			driver.findElement(By.xpath("//div[@id='geoitem1']")).click();
			ip.isElementClickableByXpath(driver, "//input[@id='zip']", 60);
			assertEquals(driver.findElement(By.xpath("//input[@id='zip']")).getAttribute("value"), "2446");
			ip.isElementClickableByXpath(driver, "//input[@id='city']", 60);
			assertEquals(driver.findElement(By.xpath("//input[@id='city']")).getAttribute("value"), "PEMBROOKE");
			ip.isElementClickableByXpath(driver, "//select[@id='stateselectbox']", 60);
			assertEquals(u.getFirstSelectedOptionFromSelect(driver, "//select[@id='stateselectbox']"), "NSW - New South Wales");
		}
		else {
			ip.isElementClickableByXpath(driver, "//input[@id='zip']", 60);
			driver.findElement(By.xpath("//input[@id='zip']")).sendKeys("1234");
			ip.isElementClickableByXpath(driver, "//select[@id='regionChangeDropdown']", 60);
			new Select(driver.findElement(By.xpath("//select[@id='regionChangeDropdown']"))).selectByVisibleText("North, Central and South America");
			ip.isElementClickableByXpath(driver, "//select[@id='localeChangeDropdown']", 60);
			new Select(driver.findElement(By.xpath("//select[@id='localeChangeDropdown']"))).selectByVisibleText("United States");
			ip.isElementClickableByXpath(driver, "//input[@id='address1']", 60);
			driver.findElement(By.xpath("//input[@id='address1']")).clear();
			driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("13 Route 111");
			ip.isElementPresentByXPATH(driver, "//div[@id='geoitem1']");
			assertEquals(driver.findElement(By.xpath("//div[@id='geoitem1']")).getText(), "13 Route 111, Derry, NH 03038");
			driver.findElement(By.xpath("//div[@id='geoitem1']")).click();
			ip.isElementClickableByXpath(driver, "//input[@id='zip']", 60);
			assertEquals(driver.findElement(By.xpath("//input[@id='zip']")).getAttribute("value"), "03038");
			ip.isElementClickableByXpath(driver, "//input[@id='city']", 60);
			assertEquals(driver.findElement(By.xpath("//input[@id='city']")).getAttribute("value"), "Derry");
			ip.isElementClickableByXpath(driver, "//select[@id='stateselectbox']", 60);
			assertEquals(u.getFirstSelectedOptionFromSelect(driver, "//select[@id='stateselectbox']"), "NH - New Hampshire");
		}

		ip.isElementClickableByXpath(driver,"//input[@id='company-nf']", 60);
		driver.findElement(By.xpath("//input[@id='company-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='company-nf']")).sendKeys("PRECISELY");
		driver.findElement(By.xpath("//input[@id='contactnumber-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='contactnumber-nf']")).sendKeys("1234567890");

		if(!addProductFlow.startsWith("mmoAutomated")) {
			ip.isElementClickableByXpath(driver, "//input[@id='firstname-nf']", 60);
			driver.findElement(By.xpath("//input[@id='firstname-nf']")).clear();
			driver.findElement(By.xpath("//input[@id='firstname-nf']")).sendKeys(this.userFirstName);
			driver.findElement(By.xpath("//input[@id='lastname-nf']")).clear();
			driver.findElement(By.xpath("//input[@id='lastname-nf']")).sendKeys(this.userSecondName);
			this.userEmailId = this.userFirstName + "+" + this.userSecondName + "@gmail.com";
			driver.findElement(By.xpath("//input[@id='email-nf']")).clear();
			driver.findElement(By.xpath("//input[@id='email-nf']")).sendKeys(this.userEmailId);
			driver.findElement(By.xpath("//input[@id='verifyemail']")).clear();
			driver.findElement(By.xpath("//input[@id='verifyemail']")).sendKeys(this.userEmailId);
		}else{
			ip.isURLContains(driver, "addproduct");
			ip.isGetTextContainsByXPATH(driver, "//div[@id='alertsuccess']/div[2]/span",
					"Welcome back. To add MapMarker to your account, fill in your details below and click the \"Add Product\" button");
			int plusIndex, attherateIndex;
			plusIndex = addProductFlow.indexOf("+");
			attherateIndex = addProductFlow.indexOf("@");
			this.userFirstName = addProductFlow.substring(0, plusIndex);
			this.userSecondName = addProductFlow.substring(plusIndex + 1, attherateIndex);
			this.userEmailId = addProductFlow;
			assertEquals(driver.findElement(By.xpath("//input[@id='firstname-nf']")).getAttribute("value"), this.userFirstName);
			assertEquals(driver.findElement(By.xpath("//input[@id='lastname-nf']")).getAttribute("value"), this.userSecondName);
			assertEquals(driver.findElement(By.xpath("//input[@id='email-nf']")).getAttribute("value"), this.userEmailId);
			assertEquals(driver.findElement(By.xpath("//button[@id='createaccbtn-nf']/span[2]")).getText(), "Add Product");
		}

		System.out.println("**" + this.userFirstName + "**" + this.userSecondName + "**" + this.userEmailId);

		verifyfooters();

		ip.isElementClickableByXpath(driver, "//button[@id='createaccbtn-nf']", 60);
		u.clickByJavaScript(driver, "//button[@id='createaccbtn-nf']");
		if(!plan.equalsIgnoreCase("free")) {
			enterPaymentDetails();
		}
		if(!addProductFlow.startsWith("mmoAutomated")) {
			ip.isGetTextContainsByXPATH(driver, "//p[@id='successtext']", "Success!");
			ip.isGetTextContainsByXPATH(driver, "//p[@id='youraccttext']", "Thank you for registering for MapMarker. "
					+ "Please check your email to complete your registration process. You may safely close this window.");
		}else{
			ip.isGetTextContainsByXPATH(driver, "//p[@id='successtext']", "Success!");
			ip.isGetTextContainsByXPATH(driver, "//p[@id='youraccttext']", "Your subscription has been added. Redirecting to Dashboard.");
			ip.isURLContains(driver, "/dashboard");
			ip.isGetTextContainsByXPATH(driver, "//a/div/div", this.userFirstName + " " + this.userSecondName);
		}
	}

	private void navigateToPlan(String plan) {

		if(!driver.getCurrentUrl().contains("signup")){
			switch (plan) {
				case "free":
					ip.isElementClickableByXpath(driver, "//a[@onclick=\"selectPlan('?plan=free')\"]", 60);
					u.clickByJavaScript(driver, "//a[@onclick=\"selectPlan('?plan=free')\"]");
					ip.isURLContains(driver, "signup/geocoding?plan=free");
					this.userFirstName = "mmoAutomated";
					this.userSecondName = "FreeUS" + this.dateAndTime;
					break;
				case "5k":
					ip.isElementClickableByXpath(driver, "//a[@onclick=\"selectPlan('?plan=gc_5k_monthly')\"]", 60);
					u.clickByJavaScript(driver, "//a[@onclick=\"selectPlan('?plan=gc_5k_monthly')\"]");
					ip.isURLContains(driver, "signup/geocoding?plan=gc_5k_monthly");
					this.userFirstName = "mmoAutomated";
					this.userSecondName = "5k" + this.dateAndTime;
					break;
				case "prof":
					ip.isElementClickableByXpath(driver, "//a[@onclick=\"selectPlan('?plan=gc_300k_quarterly')\"]", 60);
					u.clickByJavaScript(driver, "//a[@onclick=\"selectPlan('?plan=gc_300k_quarterly')\"]");
					ip.isURLContains(driver, "signup/geocoding?plan=gc_300k_quarterly");
					this.userFirstName = "mmoAutomated";
					this.userSecondName = "Prof" + this.dateAndTime;
			}
		}
	}

	public void enterPaymentDetails(){
		ip.isURLContains(driver, "payment/geocoding");
		ip.isGetTextContainsByXPATH(driver, "//div[@id='paymentinfoheading']", "Payment Info");
		ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Payment')]");
		ip.frameToBeAvailableAndSwitchToIt(driver, "paymetricsForm");
		ip.isElementClickableByXpath(driver, "//input[@id='c-cardname']", 60);
		driver.findElement(By.xpath("//input[@id='c-cardname']")).clear();
		driver.findElement(By.xpath("//input[@id='c-cardname']")).sendKeys("CARD NAME");
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

		verifyfooters();

		ip.isElementClickableByXpath(driver, "//button[@id='activateplanbutton']", 60);
		u.clickByJavaScript(driver, "//button[@id='activateplanbutton']");
		ip.isURLContains(driver, "thanks/geocoding");
	}

	/**
	 *
	 */
	public void signUpGeoTaxUser() {
		this.dateAndTime = u.currentDateTime();
		this.userFirstName = "mmoAutomated";
		this.userSecondName = "geoTax" + this.dateAndTime;
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'Try GeoTAX for Free')]", 60);
		driver.findElement(By.xpath("//a[contains(text(),'Try GeoTAX for Free')]")).click();
		ip.isElementClickableByXpath(driver, "//button[@id='choosebtn8']/span", 60);
		driver.findElement(By.xpath("//button[@id='choosebtn8']/span")).click();
		ip.isElementClickableByXpath(driver, "//input[@id='firstname-nf']", 60);
		driver.findElement(By.xpath("//input[@id='firstname-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='firstname-nf']")).sendKeys(this.userFirstName);
		ip.isElementClickableByXpath(driver, "//input[@id='lastname-nf']", 60);
		driver.findElement(By.xpath("//input[@id='lastname-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='lastname-nf']")).sendKeys(this.userSecondName);
		this.userEmailId = this.userFirstName + "+" + this.userSecondName + "@gmail.com";
		driver.findElement(By.xpath("//input[@id='email-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='email-nf']")).sendKeys(this.userEmailId);
		driver.findElement(By.xpath("//input[@id='verifyemail']")).clear();
		driver.findElement(By.xpath("//input[@id='verifyemail']")).sendKeys(this.userEmailId);
		driver.findElement(By.xpath("//input[@id='company-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='company-nf']")).sendKeys("PRECISELY");

		verifyfooters();

		ip.isElementClickableByXpath(driver, "//button[@id='createaccbtn-nf']/span[2]", 60);
		u.clickByJavaScript(driver, "//button[@id='createaccbtn-nf']/span[2]");
		ip.isGetTextContainsByXPATH(driver, "//p[@id='successtext']", "Success!");
		ip.isGetTextContainsByXPATH(driver, "//p[@id='youraccttext']", "Thank you for registering for GeoTAX. " +
				"Please check your email to complete your registration process. You may safely close this window.");
		System.out.println("**" + this.userFirstName + "**" + this.userSecondName + "**" + this.userEmailId);
	}

	private void verifyfooters() {
		//Terms of Use
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'Terms of Use')]", 60);
		u.clickByJavaScript(driver, "//a[contains(text(),'Terms of Use')]");
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Terms of Use - Precisely", ip);

		//Privacy
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'Privacy')]", 60);
		u.clickByJavaScript(driver, "//a[contains(text(),'Privacy')]");
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Privacy Policy - Precisely", ip);
	}

	public void navigateToSignUpForAddProductFlow(String plan, String userID) {
		navigateToPlan(plan);
		ip.isElementClickableByXpath(driver, "//input[@id='email-nf']", 60);
		driver.findElement(By.xpath("//input[@id='email-nf']")).clear();
		driver.findElement(By.xpath("//input[@id='email-nf']")).sendKeys(userID);
		driver.findElement(By.xpath("//input[@id='verifyemail']")).sendKeys(userID);
		ip.isElementClickableByXpath(driver, "//div[@id='alertsuccess']/div[2]/span", 60);
		ip.isGetTextContainsByXPATH(driver, "//div[@id='alertsuccess']/div[2]/span",
				"Welcome back. Please verify your credentials to add MapMarker subscription to your account.");
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'Click here to proceed.')]", 60);
		driver.findElement(By.xpath("//a[contains(text(),'Click here to proceed.')]")).click();
	}

	/**
	 * @return userDetails
	 */
	public String getUserDetails() {
		return this.userEmailId;
	}
}
