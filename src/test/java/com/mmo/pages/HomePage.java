package com.mmo.pages;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

import com.mmo.util.BaseClass;


public class HomePage extends BaseClass{

	/*
	 * 	
	 */
	public void verifyHomePage() {
		assertEquals(driver.findElement(By.xpath("//a[contains(text(),'MapMarker')]")).getText(), "MapMarker");
		assertEquals(driver.findElement(By.cssSelector(".bannerContent > h1")).getText(), "Geocoding made easy.");
		assertEquals(driver.findElement(By.cssSelector("h2:nth-child(2)")).getText(), "Pitney Bowes® world-class geocoding is now available as an easy-to-use, scalable web application. MapMarkerTM online converts addresses to coordinates with accuracy and speed for improved mapping and location intelligence.");
		assertEquals(driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=free')\"]")).getText(), "Choose");
		assertEquals(driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=gc_5k_monthly')\"]")).getText(), "Choose");
		//help
			driver.findElement(By.cssSelector("#navbarSupportedContent > ul.navbar-nav.navbar-right > li.nav-item.menu-help > a > svg")).click();
			u.waitForNumberOfWindowsToEqual(driver, 60, 2);
			u.verifyWindowTitle(driver, "MapMarker - Introduction", ip);
		//
		u.clickByJavaScript(driver, "//a[@onclick='showOverageModal()'][1]");
		ip.isTextPresentByXPATH(driver, "//h5", "About Plan Overages");
		assertEquals(driver.findElement(By.xpath("//div[2]/div/div/div[2]/p")).getText(), "Your plan includes a maximum number of geocodes per month. "
				+ "If you use more than that, we will bill you at the same \"per geocode\" price for your geocoding as you paid before you went over the limit.");
		driver.findElement(By.xpath("(//button[@type='button'])[2]")).click();
		assertEquals(driver.findElement(By.xpath("//a[@onclick=\"selectPlan('?plan=starter_100k_monthly')\"]")).getText(), "Choose");
		u.clickByJavaScript(driver, "(//a[@onclick='showOverageModal()'])[2]");
		ip.isTextPresentByXPATH(driver, "//h5", "About Plan Overages");
		assertEquals(driver.findElement(By.xpath("//div[2]/div/div/div[2]/p")).getText(), "Your plan includes a maximum number of geocodes per month. "
				+ "If you use more than that, we will bill you at the same \"per geocode\" price for your geocoding as you paid before you went over the limit.");
		u.actionBuilderClick(driver, "//div[@id='overageMessageModal']/div/div/div[2]/button");		
		assertEquals(driver.findElement(By.xpath("//a[contains(@href, 'mailto:Saassalessupport@pb.com')]")).getText(), "Email");
	}	

}
