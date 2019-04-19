package com.mmo.pages;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.Select;

import com.mmo.util.BaseClass;

public class JobPage extends BaseClass{

	//private static String jobDetails[] = new String[3];
	private static String outputFileName;
	private static String jobStatus;
	private String jobId;
	private String jobStreetGeocodes;
	private String jobFallbackGeocodes;
	private String jobFailedGeocodes;
	private String jobTotalRecords;
	

	public void uploadFileConfigureAndStartJob(String inputFileName, String dragColumns, String dropFieldsToGeocode, String outputFields,
			String outputFormat, String country, String matchMode) {

		ip.isElementClickableByXpath(driver, "//a[contains(text(),'CSV')]", 60);

		String file = null;
		try {
			file = directory.getCanonicalPath() + File.separator + "data" + File.separator + "expectedFiles" + File.separator + inputFileName;
		} catch (IOException ex) {
			Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
		}
		WebElement elm = driver.findElement(By.xpath("//input[@type='file']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; arguments[0].style.width = '1px'; arguments[0].style.opacity = 1", elm);
		elm.sendKeys(file);
		ip.isURLContains(driver, "configureGeocoder");
		ip.isTextPresentByXPATH(driver, "//div[@id='toast-container']/div/div", "File successfully uploaded and geocoding options configured.");
		ip.isTextPresentByXPATH(driver, "//h1", "Step 2: Input Options");

		if(!(dragColumns == "" || dragColumns == null || dragColumns.equalsIgnoreCase("NA"))) {
			String[] dragCols = dragColumns.split(",");
			String[] dropFields = dropFieldsToGeocode.split(",");
			Assert.assertTrue("Drag Columns count is not equal to drop Fields", dragCols.length == dropFields.length);
			WebElement dragElement = null, dropElement = null;
			String xpath = null;

			int x = 0;
			for (String cols : dragCols) {
				dragElement = driver.findElement(By.linkText(cols));
				switch (dropFields[x].trim()) {
				case "Address":
					dropElement = driver.findElement(By.xpath("//div[@id='streetNameColumn']"));
					xpath = "//div[@id='streetNameColumn']/div/a";
					System.out.print("****in Address****" + "\n");
					break;
				case "City":
					dropElement = driver.findElement(By.xpath("//div[@id='cityColumn']"));
					xpath = "//div[@id='cityColumn']/div/a";
					break;
				case "State":
					dropElement = driver.findElement(By.xpath("//div[@id='stateProvinceColumn']"));
					xpath = "//div[@id='stateProvinceColumn']/div/a";
					break;
				case "Postcode":
					dropElement = driver.findElement(By.xpath("//div[@id='postCodeColumn']"));
					xpath = "//div[@id='postCodeColumn']/div/a";
					break;
				case "Country":
					dropElement = driver.findElement(By.xpath("//div[@id='countryColumn']"));
					xpath = "//div[@id='countryColumn']/div/a";
					break;
				default:
					u.illegalStateException("Unknown drop field provided: " + dropFields[x].trim());
				}
				int z = 0;
				while(z < 5) 
				{
					try {
						Actions builder = new Actions(driver);  // Configure the Action
						((Locatable)dropElement).getCoordinates().inViewPort();
						builder.dragAndDrop(dragElement, dropElement).build().perform();
						ip.isTextPresentByXPATH(driver, xpath, cols, 15);
						x++;
						z=5;
					}catch (Exception e) {
						z++;
						System.out.print("****IN CATCH****" + "\n");
						if(z == 5)
						{
							u.illegalStateException("Unable to drag column to geocode field after mupltile tries: " + cols);
						}
					}
				}
			}
		}

		if(outputFields.equalsIgnoreCase("all")) {
			driver.findElement(By.xpath("//button[@id='moveAllToRight']")).click();
			List<WebElement> rightElements = driver.findElements(By.xpath("//div[@class='output-field-drop']/div"));
			System.out.print("****rightElements1****: " + rightElements.size() + "\n");
			Assert.assertTrue("All output columns not equal to 23", rightElements.size() == 23);
		}else {
			List<WebElement> rightElements = driver.findElements(By.xpath("//div[@class='output-field-drop']/div"));
			Assert.assertTrue("Default output columns not equal to 5", rightElements.size() == 5);
		}

		new Select(driver.findElement(By.xpath("//select[@id='selectExportFormat']"))).selectByVisibleText(outputFormat.toUpperCase());

		int idx;
		if(inputFileName.contains(".csv")) {
			idx = inputFileName.lastIndexOf("csv");
		}else {
			idx = inputFileName.lastIndexOf("zip");
		}
		outputFileName = inputFileName.substring(0, idx - 1) + "_" + u.currentDateTime();
		System.out.print("****outputFileName**** : " + outputFileName + "\n");
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(outputFileName);

		driver.findElement(By.xpath("//button[@id='nextBtn']")).click();

		if(!country.equalsIgnoreCase("Mapped")) {
			ip.isTextPresentByXPATH(driver, "//ngb-modal-window/div/div/div/h5", "Country Not Mapped");
			ip.isTextPresentByXPATH(driver, "//p", "You have not mapped country column from uploaded file with country box in fields to geocode section. "
					+ "For accurate and faster results, it is recommended to map country especially if your file contains addresses from "
					+ "multiple countries.");
			ip.isElementClickableByXpath(driver, "(//button[@type='button'])[4]", 60);
			driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
			ip.isTextPresentByXPATH(driver, "//h1", "Step 3: Geocode Options");
			ip.isElementClickableByXpath(driver, "//select", 60);
			new Select(driver.findElement(By.xpath("//select"))).selectByVisibleText(country);
		}else {
			ip.isTextPresentByXPATH(driver, "//h1", "Step 3: Geocode Options");
			if(driver.findElement(By.xpath("//input[@placeholder='Already mapped']")).isEnabled()){
				u.illegalStateException("Country dropdown should be disabled");
			}
		}

		ip.isElementClickableByXpath(driver, "//button[@id='startJobBtn']", 60);
		new Select(driver.findElement(By.xpath("//select[@id='selectMatchMode']"))).selectByVisibleText(matchMode);
		driver.findElement(By.xpath("//button[@id='startJobBtn']")).click();
		ip.isTextPresentByXPATH(driver, "//div[2]/div/div", "Your job \"" + outputFileName + "\" has "
				+ "been successfully started. We will notify you once it is complete.");
		ip.isElementClickableByXpath(driver, "//input[@id='titleFilter']", 60);
		driver.findElement(By.xpath("//input[@id='titleFilter']")).clear();
		driver.findElement(By.xpath("//input[@id='titleFilter']")).sendKeys(outputFileName);
		ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
		ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outputFileName);
		ip.isTextPresentByXPATH(driver, "//tr[1]/td[2]", outputFormat.toUpperCase());
		waitforJobToGetCompleteAndReturnStatus(outputFileName);
		getJobDetailsAfterCompletion(outputFileName);
	}

	public void waitforJobToGetCompleteAndReturnStatus(String outputFileName) {
		ip.isElementClickableByXpath(driver, "//input[@id='titleFilter']", 60);
		driver.findElement(By.xpath("//input[@id='titleFilter']")).clear();
		driver.findElement(By.xpath("//input[@id='titleFilter']")).sendKeys(outputFileName);
		ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
		ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outputFileName);
		int x = 0;
		do {
			System.out.print("****x Value**** : " + x + "\n");
			ip.isElementClickableByXpath(driver, "//button[@id='refreshDashboard']", 60);
			if(driver.findElement(By.xpath("//tr[1]/td[7]")).getText().equalsIgnoreCase("Geocoding")) {
				driver.findElement(By.xpath("//button[@id='refreshDashboard']")).click();
				try {
					Thread.sleep(30000);
					x++;
					if(x == 10)
					{
						u.illegalStateException("File running since 300 secs :" + outputFileName);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				this.jobStatus = driver.findElement(By.xpath("//tr[1]/td[7]")).getText();
				driver.navigate().refresh();
				break;
			}
		} while(x < 10);
		
		System.out.print("****jobStatus**** : " + this.jobStatus + "\n");
	}

	public void getJobDetailsAfterCompletion(String outputFileName) {
		ip.isElementClickableByXpath(driver, "//input[@id='titleFilter']", 60);
		System.out.print("****aage****" + "\n");
		driver.findElement(By.xpath("//input[@id='titleFilter']")).clear();
		driver.findElement(By.xpath("//input[@id='titleFilter']")).sendKeys(outputFileName);
		ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
		ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outputFileName);
		ip.isElementClickableByXpath(driver, "//tr[1]/td[8]/a[1]/i", 60);
		driver.findElement(By.xpath("//tr[1]/td[8]/a[1]/i")).click();
		ip.isElementClickableByXpath(driver, "//a[@id='downloadFile']", 60);
		driver.findElement(By.xpath("//h1")).getText().contains(outputFileName);
		this.jobId = driver.findElement(By.xpath("//div/div/div/div/div")).getText();
		this.jobStreetGeocodes = driver.findElement(By.xpath("//div[@id='streetBuilding']")).getText();
		this.jobFallbackGeocodes = driver.findElement(By.xpath("//div[@id='geographicPostal']")).getText();
		this.jobFailedGeocodes = driver.findElement(By.xpath("//h1[@id='failedRecordCount']")).getText();
		this.jobTotalRecords = driver.findElement(By.xpath("//div[@id='totalRecords']")).getText();
		System.out.print("****jobId****:" + this.jobId.substring(this.jobId.indexOf(":") + 2, this.jobId.length()) + "\n");
		System.out.print("****jobStreetGeocodes****:" + 
				this.jobStreetGeocodes.substring(this.jobStreetGeocodes.indexOf(":") + 2, this.jobStreetGeocodes.length()) + "\n");
		System.out.print("****jobFallbackGeocodes****:" + 
				this.jobFallbackGeocodes.substring(this.jobFallbackGeocodes.indexOf(":") + 2, this.jobFallbackGeocodes.length()) + "\n");
		System.out.print("****jobFailedGeocodes****:" + 
				this.jobFailedGeocodes.substring(this.jobFailedGeocodes.indexOf(":") + 1, this.jobFailedGeocodes.length()) + "\n");
		System.out.print("****jobTotalRecords****:" + 
				this.jobTotalRecords.substring(this.jobTotalRecords.indexOf(":") + 2, this.jobTotalRecords.length()) + "\n");
	}


	/**
	 * 
	 * @return
	 */
	public static String getOutputFileName() {
		return outputFileName;
	}
}
