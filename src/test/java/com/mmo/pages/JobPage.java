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
import org.testng.Reporter;

import com.mmo.util.BaseClass;
import com.mmo.util.UnzipUtility;

public class JobPage extends BaseClass {

	private String outputFileName;
	private String jobStatus;
	private String jobId;
	private String jobStreetGeocodes;
	private String jobFallbackGeocodes;
	private String jobFailedGeocodes;
	private String jobTotalRecords;

	/**
	 * 
	 * @param inputFileName
	 * @param dragColumns
	 * @param dropFieldsToGeocode
	 * @param outputFields
	 * @param outputFormat
	 * @param country
	 * @param matchMode
	 */
	public void uploadFileConfigureAndStartJob(String secondName, String inputFileName, String geocodingType, String autoDrag, String dragColumns,
			String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
			String matchMode, String totalRecords) {

		ip.isElementClickableByXpath(driver, "//div/i", 60);
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'CSV')]", 60);
		
		String file = null;
		try {
			file = directory.getCanonicalPath() + File.separator + "data" + File.separator + "inputFiles"
					+ File.separator + inputFileName;
		} catch (IOException ex) {
			Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		WebElement elm;
		if(geocodingType.equalsIgnoreCase("forward")) {
			driver.findElement(By.xpath("//div[2]/div/ul/li[1]/a")).click();
			ip.isTextPresentByXPATH(driver, "//h2", "Geocoding");
			elm = driver.findElement(By.xpath("//input[@type='file']"));
		}else {
			driver.findElement(By.xpath("//div[2]/div/ul/li[2]/a")).click();
			ip.isTextPresentByXPATH(driver, "//div[2]/h2", "Reverse Geocoding");
			elm = driver.findElement(By.xpath("(//input[@type='file'])[2]"));
		}
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; arguments[0].style.width = '1px'; arguments[0].style.opacity = 1",
				elm);
		elm.sendKeys(file);
		ip.isURLContains(driver, "configureGeocoder");
		ip.isTextPresentByXPATH(driver, "//div[@id='toast-container']/div/div","File successfully uploaded and geocoding options configured.");
		ip.isTextPresentByXPATH(driver, "//h1", "Step 2: Input Options");

		if(driver.getCurrentUrl().contains("?isFirstJob=true")){
			System.out.println("***inside first job***");
			ip.isTextPresentByXPATH(driver, "//div[@id='inputFieldViewAnchor']/div/div/div/h5",
					"We filled in some of the fields below, if you need to replace them drag and drop your columns" +
							" from uploaded records snapshot.");
			driver.findElement(By.xpath("//div[@id='inputFieldViewAnchor']/div/div/div/button")).click();
		}

		if (!(dragColumns == "" || dragColumns == null || dragColumns.equalsIgnoreCase("NA"))) {
			String[] dragCols = dragColumns.split(",");
			String[] dropFields = dropFieldsToGeocode.split(",");
			Assert.assertTrue("Drag Columns count is not equal to drop Fields", dragCols.length == dropFields.length);
			
			WebElement dragElement = null, dropElement = null;
			String xpath = null;

			int x = 0;
			for (String col : dragCols) {
				dragElement = driver.findElement(By.linkText(col));
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
				case "Longitude":
					dropElement = driver.findElement(By.xpath("//div[@id='longitudeColumn']"));
					xpath = "//div[@id='longitudeColumn']/div/a";
					break;
				case "Latitude":
					dropElement = driver.findElement(By.xpath("//div[@id='latitudeColumn']"));
					xpath = "//div[@id='latitudeColumn']/div/a";
					break;
				case "Country":
					dropElement = driver.findElement(By.xpath("//div[@id='countryColumn']"));
					xpath = "//div[@id='countryColumn']/div/a";
					break;
				default:
					u.illegalStateException("Unknown drop field provided: " + dropFields[x].trim());
				}

				if (autoDrag.equalsIgnoreCase("Y")) {
					try {
						ip.isTextPresentByXPATH(driver, xpath, col, 15);
						x++;
					} catch (Exception e) {
						System.out.print("****AUTO DRAG****" + "\n");
						u.illegalStateException("Field not autoDragged: " + col);
					}
				} else {
					int z = 0;
					while (z < 5) {
						try {
							Actions builder = new Actions(driver); // Configure the Action
							((Locatable) dropElement).getCoordinates().inViewPort();
							builder.dragAndDrop(dragElement, dropElement).build().perform();
							ip.isTextPresentByXPATH(driver, xpath, col, 15);
							x++;
							z = 5;
						} catch (Exception e) {
							z++;
							System.out.print("****IN CATCH****" + "\n");
							if (z == 5) {
								u.illegalStateException(
										"Unable to drag column to geocode field after multiple tries: " + col);
							}
						}
					}
				}
			}
		}

		if (outputFields.equalsIgnoreCase("All")) {
			driver.findElement(By.xpath("//button[@id='moveAllToRight']")).click();
			List<WebElement> rightElements = driver.findElements(By.xpath("//div[@class='output-field-drop']/div"));
			if(geocodingType.equalsIgnoreCase("forward")) {
				Assert.assertTrue("All output columns not equal to 35, actual fields are " + rightElements.size(), rightElements.size() == 35);
			}else {
				Assert.assertTrue("All output columns not equal to 36, actual fields are " + rightElements.size(), rightElements.size() == 36);
			}
		} else if(outputFields.equalsIgnoreCase("Default")) {
			List<WebElement> rightElements = driver.findElements(By.xpath("//div[@class='output-field-drop']/div"));
			Assert.assertTrue("Default output columns not equal to 5", rightElements.size() == 5);
		} else{
			if(geocodingType.equalsIgnoreCase("forward")) {
				driver.findElement(By.xpath("//div[@id='formattedStreetAddress']")).click();
				driver.findElement(By.xpath("//div[@id='formattedLocationAddress']")).click();
			}else{
				driver.findElement(By.xpath("//div[@id='X']")).click();
				driver.findElement(By.xpath("//div[@id='Y']")).click();
			}
			driver.findElement(By.xpath("//button[@id='moveToRight']")).click();
			List<WebElement> rightElements = driver.findElements(By.xpath("//div[@class='output-field-drop']/div"));
			Assert.assertTrue("Some output columns not equal to 7", rightElements.size() == 7);
		}

		new Select(driver.findElement(By.xpath("//select[@id='selectExportFormat']"))).selectByVisibleText(outputFormat.toUpperCase());

		int idx;
		if (inputFileName.contains(".csv")) {
			idx = inputFileName.lastIndexOf("csv");
		} else {
			idx = inputFileName.lastIndexOf("zip");
		}
		outputFileName = inputFileName.substring(0, idx - 1) + "_" + secondName + u.getMilliSeconds();
		System.out.print("****outputFileName**** : " + outputFileName + "\n");
		driver.findElement(By.xpath("//input[@type='text']")).clear();
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(outputFileName);
		
		if(geocodingType.equalsIgnoreCase("forward") && outputFormat.equalsIgnoreCase("CSV")) {
			if(!driver.findElement(By.xpath("/html/body/app-root/div/jhi-configuration/div[1]/div[10]/div[3]/input")).isEnabled()) {
				System.out.print("****COORDINATE DROPDOWN DISABLED****: " + "\n");
				System.out.print("****VALUE****: " + 
						driver.findElement(By.xpath("/html/body/app-root/div/jhi-configuration/div[1]/div[10]/div[3]/input")).getAttribute("value") + "\n");
			}	
		}else if(geocodingType.equalsIgnoreCase("forward") &&
				(outputFormat.equalsIgnoreCase("TAB")|| outputFormat.equalsIgnoreCase("SHP"))){
			System.out.print("****COORDINATE DROPDOWN ENABLED****: " + "\n");
			new Select(driver.findElement(By.xpath("//div[3]/select"))).selectByVisibleText(coordSystem);
		}

		driver.findElement(By.xpath("//button[@id='nextBtn']")).click();

		if(geocodingType.equalsIgnoreCase("forward")) {
			if (!country.startsWith("Mapped")) {
				ip.isTextPresentByXPATH(driver, "//ngb-modal-window/div/div/div/h5", "Country Not Mapped");
				ip.isTextPresentByXPATH(driver, "//p",
						"You have not mapped country column from uploaded file with country box in fields to geocode section. "
								+ "For accurate and faster results, it is recommended to map country especially if your file contains addresses from "
								+ "multiple countries.");
				ip.isElementClickableByXpath(driver, "(//button[@type='button'])[4]", 60);
				driver.findElement(By.xpath("(//button[@type='button'])[4]")).click();
				ip.isTextPresentByXPATH(driver, "//h1", "Step 3: Geocode Options");
				ip.isElementClickableByXpath(driver, "//select", 60);
				new Select(driver.findElement(By.xpath("//select"))).selectByVisibleText(country);
			} else {
				ip.isTextPresentByXPATH(driver, "//h1", "Step 3: Geocode Options");
				if (driver.findElement(By.xpath("/html/body/app-root/div/geocode-options/div[1]/div/div[2]/div[1]/input")).isEnabled()) {
					u.illegalStateException("Country dropdown should be disabled");
				}
			}	
			ip.isElementClickableByXpath(driver, "//button[@id='startJobBtn']", 60);
			new Select(driver.findElement(By.xpath("//select[@id='selectMatchMode']"))).selectByVisibleText(matchMode);
		}else {
			ip.isTextPresentByXPATH(driver, "//h1", "Step 3: Geocode Options");
			ip.isTextPresentByXPATH(driver, "//div[1]/h5", "Distance");
			ip.isTextPresentByXPATH(driver, "//div[2]/h5", "Coordinate System");
			new Select(driver.findElement(By.xpath("//div[2]/select"))).selectByVisibleText(coordSystem);
		}
		
		driver.findElement(By.xpath("//button[@id='startJobBtn']")).click();
		ip.isTextPresentByXPATH(driver, "//div[2]/div/div", "Your job \"" + outputFileName + "\" has "
				+ "been successfully started. We will notify you once it is complete.");
		ip.isElementClickableByXpath(driver, "//input[@id='titleFilter']", 60);
		driver.findElement(By.xpath("//input[@id='titleFilter']")).clear();
		driver.findElement(By.xpath("//input[@id='titleFilter']")).sendKeys(outputFileName);
		ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
		ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outputFileName);
		ip.isTextPresentByXPATH(driver, "//tr[1]/td[2]", outputFormat.toUpperCase());
		if(geocodingType.equalsIgnoreCase("forward")) {
			ip.isTextPresentByXPATH(driver, "//tr[1]/td[4]", "Geocoding");
		} else{
			ip.isTextPresentByXPATH(driver, "//tr[1]/td[4]", "Reverse Geocoding");
		}

		if(!secondName.contains("User")) {
			if (!driver.findElement(By.xpath("//tr[1]/td[8]")).getText().equalsIgnoreCase("Geocoding")) {
					u.illegalStateException("Job status is not geocoding");
			}
		}else {
			if (!driver.findElement(By.xpath("//tr[1]/td[7]")).getText().equalsIgnoreCase("Geocoding")) {
				u.illegalStateException("Job status is not geocoding");
			}
		}
	}

	/**
	 * 
	 * @param outputFileName
	 */
	public void waitforJobToGetComplete(String userSecondName, String outputFileName) {
		String path;
		ip.isElementClickableByXpath(driver, "//input[@id='titleFilter']", 60);
		driver.findElement(By.xpath("//input[@id='titleFilter']")).clear();
		driver.findElement(By.xpath("//input[@id='titleFilter']")).sendKeys(outputFileName);
		ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
		ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outputFileName);
		int x = 0;
		do {
			System.out.print("****x Value**** : " + x + "\n");
			ip.isElementClickableByXpath(driver, "//button[@id='refreshDashboard']", 60);
			if(!userSecondName.contains("User")) {
				path = "//tr[1]/td[8]";
			}else{
				path = "//tr[1]/td[7]";
			}
			if (driver.findElement(By.xpath(path)).getText().equalsIgnoreCase("Geocoding")) {
				driver.findElement(By.xpath("//button[@id='refreshDashboard']")).click();
				try {
					Thread.sleep(30000);
					x++;
					if (x == 10) {
						u.illegalStateException("File running since 300 secs :" + outputFileName);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				driver.navigate().refresh();
				break;
			}
		} while (x < 10);
	}

	/**
	 * 
	 * @param outputFileName
	 */
	public void getJobDetails(String outputFileName) {
		ip.isElementClickableByXpath(driver, "//input[@id='titleFilter']", 60);
		driver.findElement(By.xpath("//input[@id='titleFilter']")).clear();
		driver.findElement(By.xpath("//input[@id='titleFilter']")).sendKeys(outputFileName);
		ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
		ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outputFileName);
		ip.isElementClickableByXpath(driver, "//a[@id='downloadFile']", 60);

		this.jobStatus = driver.findElement(By.xpath("//tr[1]/td[7]")).getText();
		this.jobId = driver.findElement(By.xpath("//div/div/div/div/div")).getText();
		this.jobStreetGeocodes = driver.findElement(By.xpath("//div[@id='streetBuilding']")).getText();
		this.jobFallbackGeocodes = driver.findElement(By.xpath("//div[@id='geographicPostal']")).getText();
		this.jobFailedGeocodes = driver.findElement(By.xpath("//h1[@id='failedRecordCount']")).getText();
		this.jobTotalRecords = driver.findElement(By.xpath("//div[@id='totalRecords']")).getText();
		System.out.print(
				"****jobId****:" + this.jobId.substring(this.jobId.indexOf(":") + 2, this.jobId.length()) + "\n");
		System.out.print("****jobStreetGeocodes****:" + this.jobStreetGeocodes
				.substring(this.jobStreetGeocodes.indexOf(":") + 2, this.jobStreetGeocodes.length()) + "\n");
		System.out.print("****jobFallbackGeocodes****:" + this.jobFallbackGeocodes
				.substring(this.jobFallbackGeocodes.indexOf(":") + 2, this.jobFallbackGeocodes.length()) + "\n");
		System.out.print("****jobFailedGeocodes****:" + this.jobFailedGeocodes
				.substring(this.jobFailedGeocodes.indexOf(":") + 1, this.jobFailedGeocodes.length()) + "\n");
		System.out.print("****jobTotalRecords****:"
				+ this.jobTotalRecords.substring(this.jobTotalRecords.indexOf(":") + 2, this.jobTotalRecords.length())
				+ "\n");
	}

	/**
	 * 
	 * @param outputFileName
	 * @param outFileFormat
	 */
	public void downloadJobOutputFileAndCompare(String outputFileName, String outFileFormat) {
		boolean fileStatus;
		ip.isElementClickableByXpath(driver, "//input[@id='titleFilter']", 60);
		driver.findElement(By.xpath("//input[@id='titleFilter']")).clear();
		driver.findElement(By.xpath("//input[@id='titleFilter']")).sendKeys(outputFileName);
		ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
		ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outputFileName);
		ip.isElementClickableByXpath(driver, "//tr[1]/td[9]/a[2]/i", 60);
		driver.findElement(By.xpath("//tr[1]/td[9]/a[2]/i")).click();

		String zipActualFileName = outputFileName + ".zip";
		fileStatus = u.isFileDownloaded(defaultDownloadPath, zipActualFileName);
		if (fileStatus) {
			Reporter.log("File downloaded successfully: " + zipActualFileName + "\n");
		} else {
			softAssert.fail("Unable to download file fully until 2 mins: " + zipActualFileName);
		}

		Boolean status = null;
		UnzipUtility unzipper = new UnzipUtility();
		try {
			status = unzipper.unzip(zipActualFileName, outFileFormat);
		} catch (Exception ex) {
			// some errors occurred
			ex.printStackTrace();
		}

		if (!status) {
			u.illegalStateException("File Not found: " + outputFileName + "." + outFileFormat.toLowerCase());
		}

	}
	
	/**
	 * 
	 * @param inputFileName
	 * @param geocodingType
	 * @param expectedMessage
	 */
	public void uploadIncorrectFilesAndCheckValidations(String inputFileName, String geocodingType,
			String expectedMessage) {
		
		ip.isElementClickableByXpath(driver, "//div/i", 60);
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'CSV')]", 60);
		
		String file = null;
		try {
			file = directory.getCanonicalPath() + File.separator + "data" + File.separator + "NegativeFiles" + File.separator + inputFileName;
		} catch (IOException ex) {
			Logger.getLogger(File.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		WebElement elm;
		if(geocodingType.equalsIgnoreCase("forward")) {
			driver.findElement(By.xpath("//div[2]/div/ul/li[1]/a")).click();
			ip.isTextPresentByXPATH(driver, "//h2", "Geocoding");
			elm = driver.findElement(By.xpath("//input[@type='file']"));
		}else {
			driver.findElement(By.xpath("//div[2]/div/ul/li[2]/a")).click();
			ip.isTextPresentByXPATH(driver, "//div[2]/h2", "Reverse Geocoding");
			elm = driver.findElement(By.xpath("(//input[@type='file'])[2]"));
		}
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].style.visibility = 'visible'; arguments[0].style.height = '1px'; arguments[0].style.width = '1px'; arguments[0].style.opacity = 1",
				elm);
		elm.sendKeys(file);
		if(inputFileName.contains("Casing") || inputFileName.contains("CountryCol")) {
			ip.isTextPresentByXPATH(driver, "//div[@id='toast-container']/div/div","File successfully uploaded and geocoding options configured.");
				switch(inputFileName.substring(0, inputFileName.lastIndexOf("."))) {
				case "ForwardFileWithColRepeat_InSameCasing":
					ip.isTextPresentByXPATH(driver, "//div[@id='streetNameColumn']/div/a", "Address");
					break;
				case "ForwardFileWithColRepeat_InDiffCasing":
					ip.isTextPresentByXPATH(driver, "//div[@id='streetNameColumn']/div/a", "address");
					break;
				case "ReverseFileWithColRepeat_InSameCasing":
					ip.isTextPresentByXPATH(driver, "//div[@id='longitudeColumn']/div/a", "Longitude");
					break;
				case "ReverseFileWithColRepeat_InDiffCasing":
					ip.isTextPresentByXPATH(driver, "//div[@id='longitudeColumn']/div/a", "longiTUDE");
					break;
				default:
					System.out.print("****in DEFAULT****" + "\n");
					ip.isTextPresentByXPATH(driver, "//div[@id='countryColumn']/div/a", "Country");
			  }
			driver.findElement(By.xpath("//button[@id='nextBtn']")).click();
			ip.isTextPresentByXPATH(driver, "//div[@id='toast-container']/div/div", expectedMessage);
		}else {
			ip.isTextPresentByXPATH(driver, "//div[@id='toast-container']/div/div", expectedMessage);
		}
		Reporter.log("Verified '" + expectedMessage + "' in " + geocodingType + " geocoding.<br/>");
	}

	/**
	 * 
	 * @param outFileName
	 */
	public void verifyJobDetails(String userSecondName, String inputFileName, String geocodingType,
								 String outputFields, String outputFormat, String coordSystem, String country,
								 String matchMode, String totalRecords, String outFileName) {
		ip.isElementClickableByXpath(driver, "//input[@id='titleFilter']", 60);
		driver.findElement(By.xpath("//input[@id='titleFilter']")).clear();
		driver.findElement(By.xpath("//input[@id='titleFilter']")).sendKeys(outFileName);
		ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outFileName);
		ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
		if(!userSecondName.contains("User")) {
			ip.isTextPresentByXPATH(driver, "//tr[1]/td[8]", "Success", 10);
			ip.isElementClickableByXpath(driver, "//tr[1]/td[9]/a[1]/i", 60);
			driver.findElement(By.xpath("//tr[1]/td[9]/a[1]/i")).click();
		}else{
			ip.isTextPresentByXPATH(driver, "//tr[1]/td[7]", "Success", 10);
			ip.isElementClickableByXpath(driver, "//tr[1]/td[8]/a[1]/i", 60);
			driver.findElement(By.xpath("//tr[1]/td[8]/a[1]/i")).click();
		}
		ip.isElementClickableByXpath(driver, "//a[@id='downloadFile']", 60);
		ip.isElementClickableByXpath(driver, "//h1", 15);
		driver.findElement(By.xpath("//h1")).getText().equalsIgnoreCase(outputFileName + "." + outputFormat);
		ip.isTextPresentByCSS(driver, "div.alert.alert-success", "To view geocoded results, click on Download link.");
		ip.invisibilityOfElementByCSS(driver, "div.alert.alert-success");

		if(geocodingType.equalsIgnoreCase("reverse")){
			ip.isTextPresentByXPATH(driver, "//div[2]/div[2]/div/div/div", "Reverse Geocode Breakdown");
		}else{
			ip.isTextPresentByXPATH(driver, "//div[2]/div[2]/div/div/div", "Geocode Breakdown");
			ip.isTextPresentByID(driver, "matchMode", "Match Mode: " + matchMode, 15);
		}

		ip.isTextPresentByID(driver, "fileName", "File Name: " + inputFileName.substring(0, inputFileName.indexOf(".")), 15);
		ip.isTextPresentByID(driver, "coordinateSystemValue", "Projection: " + coordSystem.toLowerCase(), 15);
		System.out.print("****totalRecords****: " + totalRecords + "\n");
		ip.isTextPresentByXPATH(driver, "//div[@id='totalRecords']/span", totalRecords, 15);
		int successRecords = Integer.valueOf(driver.findElement(By.id("successRecordCount")).getText());
		int failedRecords = Integer.valueOf(driver.findElement(By.id("failedRecordCount")).getText());
		int actualTotalRecords = successRecords + failedRecords;
		Assert.assertTrue("Mismatch in totalRecords, expected:" + totalRecords + " whereas actual:" + actualTotalRecords,
				Integer.valueOf(totalRecords) == actualTotalRecords);
		if(country.startsWith("Mapped")){
			ip.isTextPresentByID(driver, "country", "Country: Field (" + country.substring(country.indexOf("_") + 1) + ") mapped from input file", 15);
		}else{
			ip.isTextPresentByID(driver, "country", "Country: " + country, 15);
		}

		List<WebElement> countTH = driver.findElements(By.xpath("//th[starts-with(text(),'out_')]"));
		System.out.print("****rightElements****: " + countTH.size() + "\n");
		switch (outputFields.toLowerCase()){
			case "default":
				Assert.assertTrue("Expected default count:6, actual: " + countTH.size(), countTH.size() == 6);
				break;
			case "some":
				Assert.assertTrue("Expected some count:8, actual: " + countTH.size(), countTH.size() == 8);
				break;
			default:
				if(geocodingType.equalsIgnoreCase("forward")){
					Assert.assertTrue("Expected default count:44, actual: " + countTH.size(), countTH.size() == 44);
				}else{
					Assert.assertTrue("Expected default count:37, actual: " + countTH.size(), countTH.size() == 37);
				}
		}
	}

	public void verifyJobsShownToUser(String userSecondName, String outFileName) {
		ip.isElementClickableByXpath(driver, "//input[@id='titleFilter']", 60);
		driver.findElement(By.xpath("//input[@id='titleFilter']")).clear();
		driver.findElement(By.xpath("//input[@id='titleFilter']")).sendKeys(outFileName);
		if(userSecondName.contains("User")){
			System.out.println("***IN USER***");
			if(outFileName.contains(userSecondName)){
				System.out.println("***User file found***" + outFileName);
				ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outFileName);
				ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
			}else{
				System.out.println("***User file not found***" + outFileName);
				ip.invisibilityOfElementByXpath(driver, "//tr[1]/td/div");
			}
		}else if(userSecondName.contains("Admin")){
			System.out.println("***IN ADMIN***");
			if(outFileName.contains("User") || outFileName.contains("Admin")){
				System.out.println("***Admin file found***" + outFileName);
				ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outFileName);
				ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
			}else{
				System.out.println("***Admin file not found***" + outFileName);
				ip.invisibilityOfElementByXpath(driver, "//tr[1]/td/div");
			}
		}else{
			System.out.println("***SA file found***" + outFileName);
			ip.isTextPresentByXPATH(driver, "//tr[1]/td/div", outFileName);
			ip.invisibilityOfElementByXpath(driver, "//tr[2]/td/div");
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getOutputFileName() {
		return this.outputFileName;
	}
}
