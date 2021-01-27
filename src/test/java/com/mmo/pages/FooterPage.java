package com.mmo.pages;

import com.mmo.util.BaseClass;
import org.openqa.selenium.By;

import static org.testng.Assert.assertEquals;

public class FooterPage extends BaseClass{

	public void verifyFooters() {

		// Footers
		ip.isElementClickableByXpath(driver, "//a[contains(@href, 'https://www.precisely.com')]", 60);
		driver.findElement(By.xpath("//a[contains(@href, 'https://www.precisely.com')]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Precisely - Trust your data. Build your possibilities.", ip);

		assertEquals(driver.findElement(By.xpath("//footer/div/div/div/div/span")).getText(),"© 2018, 2021 Precisely. All rights reserved.");

		// Legal
		ip.isGetTextContainsByXPATH(driver, "//footer/div/div/div[2]/div/a", "Legal");
		driver.findElement(By.xpath("//footer/div/div/div[2]/div/a")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Mapmarker Online Agreement - Precisely", ip);

		// Contact us
		ip.isGetTextContainsByXPATH(driver, "//footer/div/div/div[2]/div/a[2]", "Contact us");
		driver.findElement(By.xpath("//footer/div/div/div[2]/div/a[2]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Precisely Support", ip);
	}

	public void verifyLoginPageFooters() {

		// Footers
		ip.isElementClickableByXpath(driver, "//a[contains(@href, 'https://www.precisely.com')]", 60);
		ip.isGetTextContainsByXPATH(driver, "//div/span", "©2015,2021 Precisely. All rights reserved.");

		// Legal
		ip.isGetTextContainsByXPATH(driver, "//div[2]/div/a[1]", "Legal");

		// Privacy
		ip.isGetTextContainsByXPATH(driver, "//div[2]/div/a[2]", "Privacy");

		// Terms of use
		ip.isGetTextContainsByXPATH(driver, "//div[2]/div/a[3]", "Terms of use");
	}
}
