package com.mmo.pages;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

import com.mmo.util.BaseClass;


public class HomePage extends BaseClass{

	/*
	 * 	
	 */
	public void verifyHomePage() {
		assertEquals(driver.findElement(By.xpath("//a[contains(@href, '/dashboard')]")).getText(), "MapMarker");
		assertEquals(driver.findElement(By.cssSelector(".bannerContent > h1")).getText(), "Geocoding made easy.");
		assertEquals(driver.findElement(By.cssSelector("h2:nth-child(2)")).getText(), "Precisely world-class geocoding is now available as an easy-to-use, scalable web application. "
				+ "MapMarker converts addresses to coordinates with accuracy and speed for improved mapping and location intelligence.");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[1]/div[2]")).getText(), "Free Trial");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[2]/div[2]")).getText(), "Pay as you go 5k");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[3]/div[2]")).getText(), "Professional");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[4]/div[2]")).getText(), "Enterprise");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[1]/div[3]")).getText(), "2,500 Geocodes");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[2]/div[3]")).getText(), "5,000 Geocodes per month");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[3]/div[3]")).getText(), "300,000 Geocodes per quarter");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[4]/div[3]")).getText(), "100,000+ Geocodes per month");
		assertEquals(driver.findElement(By.xpath("//span[2]")).getText(), "FREE");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[4]/span")).getText(), "$");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[4]/span[2]")).getText(), "50");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[4]/div")).getText(), "per month");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[5]")).getText(), "5k minimum plus overages");
		assertEquals(driver.findElement(By.xpath("//div[3]/div[4]/span")).getText(), "$");
		assertEquals(driver.findElement(By.xpath("//div[3]/div[4]/span[2]")).getText(), "2700");
		assertEquals(driver.findElement(By.xpath("//div[3]/div[4]/div")).getText(), "per quarter");
		assertEquals(driver.findElement(By.xpath("//div[3]/div[5]")).getText(), "Annual Commitment" + "\n" + "10% discount!" +"\n" + "300k minimum plus overages");
		assertEquals(driver.findElement(By.xpath("//div[4]/div[4]/span")).getText(), "Monthly" + "\n" + "or" + "\n" +"Annually");
		assertEquals(driver.findElement(By.xpath("//div[4]/div[5]")).getText(), "20%+ discount based on volume!");
		assertEquals(driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=free')\"]")).getText(), "Choose");
		assertEquals(driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=gc_5k_monthly')\"]")).getText(), "Choose");
		assertEquals(driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=gc_300k_quarterly')\"]")).getText(), "Choose");
		assertEquals(driver.findElement(By.xpath("//a[contains(@href, 'https://support.precisely.com/contactus')]")).getText(), "Contact Us");
		assertEquals(driver.findElement(By.xpath("//div[1]/div[6]")).getText(), "Try our Geocoding service with up to 2,500 geocodes for 14 days.");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[7]")).getText(), "Our low-cost basic plan gives you 5,000 geocodes for $50 a month.");
		assertEquals(driver.findElement(By.xpath("//div[3]/div[7]")).getText(), "Our discounted annual plan gives you 300,000 geocodes for $2,700 a quarter.");
		assertEquals(driver.findElement(By.xpath("//div[4]/div[7]")).getText(), "Ideal for heavy Geocoding use." + "\n" + "Get special savings and incentives.");

		//Help document
		driver.findElement(By.cssSelector("svg.hiddenOnDevice")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "MapMarker - Introduction", ip);

		//Contact us from Last plan
		ip.isElementClickableByXpath(driver, "//a[contains(@href, 'https://support.precisely.com/contactus')]", 30);
		driver.findElement(By.xpath("//a[contains(@href, 'https://support.precisely.com/contactus')]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Contact Us - Precisely Support", ip);

		//Overages	
		u.clickByJavaScript(driver, "//a[@onclick='showOverageModal()'][1]");
		ip.isGetTextContainsByXPATH(driver, "//h5", "About Plan Overages");
		assertEquals(driver.findElement(By.xpath("//div[2]/div/div/div[2]/p")).getText(), "Your plan includes a maximum number of geocodes per month. "
				+ "If you use more than that, we will bill you at the same \"per geocode\" price for your geocoding as you paid before you went over the limit.");
		driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
		ip.isElementClickableByXpath(driver, "(//a[@onclick='showOverageModal()'])[2]", 30);
		u.clickByJavaScript(driver, "(//a[@onclick='showOverageModal()'])[2]");
		ip.isGetTextContainsByXPATH(driver, "//h5", "About Plan Overages");
		assertEquals(driver.findElement(By.xpath("//div[2]/div/div/div[2]/p")).getText(), "Your plan includes a maximum number of geocodes per month. "
				+ "If you use more than that, we will bill you at the same \"per geocode\" price for your geocoding as you paid before you went over the limit.");
		u.actionBuilderClick(driver, "//div[@id='overageMessageModal']/div/div/div[2]/button");
	}
}
