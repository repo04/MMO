package com.mmo.pages;

import org.openqa.selenium.By;

import com.mmo.util.BaseClass;

public class EmailPage extends BaseClass{
	
	/**
	 * 
	 */
	public void emailLogin() {
		driver.findElement(By.xpath("//input[@id='identifierId']")).clear();
		driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys("autommopb");
		driver.findElement(By.xpath("//div[@id='identifierNext']/content/span")).click();
		ip.isTextPresentByXPATH(driver, "//div[@id='profileIdentifier']", "autommopb@gmail.com");
		ip.isTextPresentByXPATH(driver, "//h1[@id='headingText']/content", "Welcome");
		driver.findElement(By.xpath("//input[@name='password']")).clear();
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("auto@321");
		driver.findElement(By.xpath("//div[@id='passwordNext']/content/span")).click();
		ip.isTitleContains(driver, "autommopb@gmail.com - Gmail");
	}
	
	/**
	 * 
	 * @param signUpUserID
	 * @param signUpFirstName
	 * @param signUpSecondName
	 */
	public void signUpUserCompleteRegisteration(String signUpUserID, String signUpFirstName, String signUpSecondName) {
		driver.findElement(By.xpath("//a[contains(text(),'Inbox')]")).click();
		driver.findElement(By.xpath("//div[3]/table/tbody/tr/td/div")).click(); //Primary
		driver.findElement(By.xpath("//tr[1]/td[6]/div/div/div/span/span")).click(); //TopEmail
		ip.isTextPresentByXPATH(driver, "//div[2]/div/div[2]/div/h2", "You're ready to start using MapMarker");
		ip.isTextPresentByXPATH(driver, "//h1", "Your MapMarker online account has been created.");
		ip.isTextPresentByXPATH(driver, "//td/p", "You're almost ready to start using Pitney Bowes MapMarker. "
				+ "Complete your registration using this email address: " + signUpUserID);
		ip.isTextPresentByXPATH(driver, "//p[3]", "Name: " + signUpFirstName + " " + signUpSecondName);
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'Complete Registration Now')]", 60);
		ip.ignoreStaleElementExceptionClickByXpath(driver, "//a[contains(text(),'Complete Registration Now')]");
		try {
			u.waitForNumberOfWindowsToEqual(driver, 90, 2);
		} catch (Exception e) {
			u.illegalStateException("Complete Registeration window not opened");
		}
		String HandleBefore = driver.getWindowHandle();
		int i = 1;
		for (String handle : driver.getWindowHandles()) {
			System.out.println("window handle: " + handle);
			driver.switchTo().window(handle);
			if (i == driver.getWindowHandles().size()) {
				try {
					ip.isURLContains(driver, "login2");
					ip.isTitleContains(driver, "Pitney Bowes");
					ip.isTextPresentByXPATH(driver, "//h2", "Welcome, " + signUpFirstName + " " + signUpSecondName);
					ip.isTextPresentByXPATH(driver, "//div/div/div", "Your Pitney Bowes username is");
					ip.isTextPresentByXPATH(driver, "//div[2]", signUpUserID);
					driver.findElement(By.xpath("//input[@id='newPassword']")).sendKeys("Pitney@123");
					driver.findElement(By.xpath("//input[@id='confirmPassword']")).sendKeys("Pitney@123");
					ip.isElementClickableByXpath(driver, "//button[@type='submit']", 60);
					driver.findElement(By.xpath("//button[@type='submit']")).click();
					ip.isTextPresentByXPATH(driver, "//div/div/div", "We ae experiencing problems on the backend. Please try again shortly.");
//					ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Sign In')]");
				} catch (Exception e) {
					driver.switchTo().window(HandleBefore);
					throw e;
				}
			}
		 i++;
		}
		driver.switchTo().window(HandleBefore);
	}

}
