package com.mmo.pages;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

import com.mmo.util.BaseClass;

public class FooterPage extends BaseClass{

	public void verifyFooter() {

		// Footers
		System.out.println("VRFY FOOTER");
		ip.isElementClickableByXpath(driver, "//a[contains(@href, 'https://www.precisely.com')]", 60);
		System.out.println("CLICK FOOTER ON DASHBOARD");
		driver.findElement(By.xpath("//a[contains(@href, 'https://www.precisely.com')]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Precisely - Trust your data. Build your possibilities.", ip);

		assertEquals(driver.findElement(By.xpath("//footer/div/div/div/div/span")).getText(),"© 2018, 2020 Precisely. All rights reserved.");

		// Legal
		driver.findElement(By.xpath("//footer/div/div/div[2]/div/a")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Mapmarker Online Agreement - Precisely", ip);

		// Contact us
		driver.findElement(By.xpath("//footer/div/div/div[2]/div/a[2]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Contact Us - Precisely Support", ip);
	}

}
