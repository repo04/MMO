package com.mmo.pages;

import org.openqa.selenium.By;
import org.testng.Reporter;

import com.mmo.util.BaseClass;

public class DashboardPage extends BaseClass{

	/**
	 * 
	 */
	public void verifyDashboard() {
		ip.isURLContains(driver, "/dashboard");
		//ip.isTextPresentByXPATH(driver, "//li[@id='userContextMenuLi']/a/div/div", "autoMMOPb FreeUS110419141114");
		ip.isTextPresentByXPATH(driver, "//h1", "MapMarker Dashboard");
		ip.isElementPresentByCSS(driver, "div.progress.customProgress");
		ip.isTextPresentByXPATH(driver, "//h2", "You have not Geocoded any address yet. Upload file to start geocoding now.");
		ip.isTextPresentByXPATH(driver, "//div[4]/div/div", "Refer the file templates to get started: CSV, SHP, or TAB");
		ip.isTextPresentByXPATH(driver, "//div[4]/div/div[2]", "Got questions? Please check our detailed documentation and FAQs.");
		ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Dashboard')]");
		ip.isTextPresentByXPATH(driver, "//button[@id='btnUploadFile']", "Upload File");
		ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Users')]");
		ip.isTextPresentByXPATH(driver, "//a[@id='btnCreateNewUser']", "Create New User");
		driver.findElement(By.xpath("//a[contains(text(),'documentation')]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Step 1: Upload File", ip);
		driver.findElement(By.xpath("//a[contains(text(),'FAQs')]")).click();
		u.waitForNumberOfWindowsToEqual(driver, 60, 2);
		u.verifyWindowTitle(driver, "Frequently Asked Questions", ip);
	}

	/**
	 * 
	 * @param defaultTemplates
	 */
	public void downloadDefaultTemplatesAndVerify(String[] defaultTemplates) {
		int count = defaultTemplates.length;
		String fileName = null;
		boolean fileStatus; 

		for (int i=0; i<count; i++) {
			switch (defaultTemplates[i]) {
			case "TAB":
				fileName = "MapMarker_Addresses_Template_TAB.zip";
				break;
			case "SHP":
				fileName = "MapMarker_Addresses_Template_SHP.zip";
				break;
			default:
				fileName = "MapMarker_Addresses_Template.csv";								
			}
			ip.isElementPresentByXPATH(driver, "//a[contains(text(),'" + defaultTemplates[i] + "')]");
			driver.findElement(By.xpath("//a[contains(text(),'" + defaultTemplates[i] + "')]")).click();
			fileStatus = u.isFileDownloaded(defaultDownloadPath, fileName);
			if(fileStatus) {
				Reporter.log("File downloaded successfully OR already exist in filesystem: " + fileName + "\n");				
			}else {
				//softAssert.assertTrue("Unable to download file fully until 2 mins: " + fileName);
				softAssert.fail("Unable to download file fully until 2 mins: " + fileName);
				//softAssert.assertAll();
			}
		}	
	}
}
