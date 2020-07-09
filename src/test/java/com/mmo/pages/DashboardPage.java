package com.mmo.pages;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.testng.Reporter;

import com.mmo.util.BaseClass;

public class DashboardPage extends BaseClass{

	/**
	 *
	 * @param userFirstName
	 * @param userSecondName
	 */
	public void verifyDashboard(String userFirstName, String userSecondName) {
		ip.isURLContains(driver, "/dashboard");
		ip.isTextPresentByXPATH(driver, "//a/div/div", userFirstName + " " + userSecondName);
		ip.isTextPresentByXPATH(driver, "//h1", "MapMarker Dashboard");
		ip.isElementPresentByCSS(driver, "div.progress.customProgress");
		ip.isTextPresentByXPATH(driver, "//h2", "You have not Geocoded any address yet. Upload file to start geocoding now.");
		ip.isTextPresentByXPATH(driver, "//div[4]/div/div", "Got questions? Please check our detailed documentation and FAQs.");
		ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Dashboard')]");
		ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Profile')]");
		switch (userSecondName.substring(0,4)){
			case "Admi":
				ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Users')]");
				ip.isElementPresentByXPATH(driver, "//a[@id='btnCreateNewUser']");
				ip.invisibilityOfElementByXpath(driver, "//a[contains(text(),'Billing & Plans')]");
				break;
			case "User":
				ip.invisibilityOfElementByXpath(driver, "//a[contains(text(),'Users')]");
				ip.invisibilityOfElementByXpath(driver, "//a[@id='btnCreateNewUser']");
				ip.invisibilityOfElementByXpath(driver, "//a[contains(text(),'Billing & Plans')]");
				break;
			default:
				ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Users')]");
				ip.isElementPresentByXPATH(driver, "//a[@id='btnCreateNewUser']");
				ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Billing & Plans')]");
		}

		ip.isTextPresentByXPATH(driver, "//button[@id='btnUploadFile']", "Upload File");

		driver.findElement(By.xpath("//a[contains(text(),'documentation')]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "MapMarker - Step 1: Upload File", ip);

		driver.findElement(By.xpath("//a[contains(text(),'FAQs')]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "MapMarker - Frequently Asked Questions", ip);
	}

	/**
	 * 
	 * @param defaultTemplates
	 */
	public void downloadAllDefaultTemplates(String[] defaultTemplates) {
		driver.findElement(By.xpath("//button[@id='btnUploadFile']")).click();
		ip.isTextPresentByXPATH(driver, "//h1", "Step 1: Upload File");
		
		u.emptyDefaultDownloadPath(defaultDownloadPath);
		
		driver.findElement(By.xpath("//div[2]/div/ul/li[2]/a")).click();
		ip.isTextPresentByXPATH(driver, "//div[2]/h2", "Reverse Geocoding");
		ip.isTextPresentByXPATH(driver, "//div[2]/p", "Reverse Geocoding returns an ADDRESS for a given X, Y "
				+ "(longitude and latitude values) coordinate.");
		
		driver.findElement(By.xpath("//div[2]/div/ul/li/a")).click();
		ip.isTextPresentByXPATH(driver, "//h2", "Geocoding");
		ip.isTextPresentByXPATH(driver, "//p", "Geocoding is the process of assigning an X, Y (longitude and latitude values) "
				+ "coordinate pair to the description of a place and to accurately identify corresponding records in one or "
				+ "more reference data sources which closely describe that place.");
		
		int count = defaultTemplates.length;
		String fileName = null;
		boolean fileStatus;
		
		for (int i=0; i<count; i++) {
			switch (defaultTemplates[i]) {
			case "FRWD TAB":
				fileName = "MapMarker_Geocoding_Template_TAB.zip";
				break;
			case "FRWD SHP":
				fileName = "MapMarker_Geocoding_Template_SHP.zip";
				break;
			case "RVRS TAB":
				fileName = "MapMarker_ReverseGeocoding_Template_TAB.zip";
				break;
			case "RVRS SHP":
				fileName = "MapMarker_ReverseGeocoding_Template_SHP.zip";
				break;
			case "RVRS CSV":
				fileName = "MapMarker_ReverseGeocoding_Template.csv";
				break;
			default:
				fileName = "MapMarker_Geocoding_Template.csv";								
			}
			if(i < 3) {
				ip.isElementPresentByXPATH(driver, "//a[contains(text(),'" + defaultTemplates[i].substring(5) + "')]");
				driver.findElement(By.xpath("//a[contains(text(),'" + defaultTemplates[i].substring(5) + "')]")).click();				
			}
			else {
				driver.findElement(By.xpath("//div[2]/div/ul/li[2]/a")).click();
				ip.isElementPresentByXPATH(driver, "(//a[contains(text(),'" + defaultTemplates[i].substring(5) + "')])[2]");
				driver.findElement(By.xpath("(//a[contains(text(),'" + defaultTemplates[i].substring(5) + "')])[2]")).click();				
			}
					
			fileStatus = u.isFileDownloaded(defaultDownloadPath, fileName);
			if(fileStatus) {
				Reporter.log("File downloaded successfully: " + fileName + "\n");				
			}else {
				//softAssert.assertTrue("Unable to download file fully until 2 mins: " + fileName);
				softAssert.fail("Unable to download file fully until 2 mins: " + fileName);
				//softAssert.assertAll();
			}
		}	
	}
}
