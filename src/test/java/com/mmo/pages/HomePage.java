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
		assertEquals(driver.findElement(By.xpath("//div[1]/div/div[2]/h2")).getText(), "Free Trial");
		assertEquals(driver.findElement(By.xpath("//div[2]/div/div[2]/h2")).getText(), "Pay as you go 5k");
		assertEquals(driver.findElement(By.xpath("//div[3]/div/div[2]/h2")).getText(), "Professional");
		assertEquals(driver.findElement(By.xpath("//div[4]/div/div[2]/h2")).getText(), "Enterprise");
		assertEquals(driver.findElement(By.xpath("//div[4]/div/div[1]/div/div[2]/div")).getText(), "2,500 Geocodes");
		assertEquals(driver.findElement(By.xpath("//div[4]/div/div[2]/div/div[2]/div")).getText(), "5,000 Geocodes per month");
		assertEquals(driver.findElement(By.xpath("//div[4]/div/div[3]/div/div[2]/div")).getText(), "300,000 Geocodes per quarter");
		assertEquals(driver.findElement(By.xpath("//div[4]/div/div[4]/div/div[2]/div")).getText(), "100,000+ Geocodes per month");
		assertEquals(driver.findElement(By.xpath("//div[1]/div/div[2]/div[2]/div")).getText(), "$0.00");
		assertEquals(driver.findElement(By.xpath("//div[2]/div/div[2]/div[2]/div")).getText(), "$50.00/month");
		assertEquals(driver.findElement(By.xpath("//div[3]/div/div[2]/div[2]/div")).getText(), "$2,700/quarter");
		assertEquals(driver.findElement(By.xpath("//div[1]/div/div[2]/div[4]")).getText(), "Try our Geocoding service with up to 2,500 geocodes for 14 days.");
		assertEquals(driver.findElement(By.xpath("//div[2]/div/div[2]/div[4]")).getText(), "Our low-cost basic plan gives you 5,000 geocodes for $50 a month.");
		assertEquals(driver.findElement(By.xpath("//div[3]/div/div[2]/div[4]")).getText(), "Our discounted annual plan gives you 300,000 geocodes for $2,700 a quarter.");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[4]/div[1]")).getText(), "Ideal for heavy Geocoding use.");
		assertEquals(driver.findElement(By.xpath("//div[2]/div[4]/div[2]")).getText(), "Get special savings and incentives.");
		assertEquals(driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=free')\"]")).getText(), "Choose");
		assertEquals(driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=gc_5k_monthly')\"]")).getText(), "Choose");
		assertEquals(driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=gc_300k_quarterly')\"]")).getText(), "Choose");
		assertEquals(driver.findElement(By.xpath("//a[contains(@href, 'https://support.precisely.com/contactus')]")).getText(), "Contact Us");
		                                          
		//Help from Enterprise plan
		driver.findElement(By.xpath("//div[4]/div/div[2]/div[3]/a")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Contact Us - Precisely Support", ip);

		//Overages	
		u.clickByJavaScript(driver, "//a[@onclick='showOverageModal()'][1]");
		ip.isTextPresentByXPATH(driver, "//h5", "About Plan Overages");
		assertEquals(driver.findElement(By.xpath("//div[2]/div/div/div[2]/p")).getText(), "Your plan includes a maximum number of geocodes per month. "
				+ "If you use more than that, we will bill you at the same \"per geocode\" price for your geocoding as you paid before you went over the limit.");
		driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
		u.clickByJavaScript(driver, "(//a[@onclick='showOverageModal()'])[2]");
		ip.isTextPresentByXPATH(driver, "//h5", "About Plan Overages");
		assertEquals(driver.findElement(By.xpath("//div[2]/div/div/div[2]/p")).getText(), "Your plan includes a maximum number of geocodes per month. "
				+ "If you use more than that, we will bill you at the same \"per geocode\" price for your geocoding as you paid before you went over the limit.");
		u.actionBuilderClick(driver, "//div[@id='overageMessageModal']/div/div/div[2]/button");
		
	}
}
