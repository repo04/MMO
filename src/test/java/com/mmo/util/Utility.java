package com.mmo.util;

import com.thoughtworks.selenium.webdriven.JavascriptLibrary;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static org.awaitility.Awaitility.await;

public class Utility {

	private int totalRows;

	/**
	 * Uses js to click on hidden element on the page by XPATH
	 *
	 * @param driver
	 * @param menuXPATH
	 */
	public void clickByJavaScript(WebDriver driver, String menuXPATH) {
		WebElement hiddenElement = driver.findElement(By.xpath(menuXPATH));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hiddenElement);
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", hiddenElement);
	}

	/**
	 * Uses js to click on hidden element on the page by CSS
	 * 
	 * @param driver
	 * @param menuCSS
	 */
	public void clickByJavaScriptUsingCSS(WebDriver driver, String menuCSS) {
		WebElement hiddenElement = driver.findElement(By.cssSelector(menuCSS));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hiddenElement);
		((JavascriptExecutor) driver).executeScript("arguments[0].click()", hiddenElement);
	}

	/**
	 * Remove user to check for another filter
	 *
	 * @param driver
	 * @param buttonRemoveUserFilter
	 */
	public void buttonRemoveUserFilter(WebDriver driver, String buttonRemoveUserFilter) {

		try {
			driver.findElement(By.name("filter[realname][0]")).click();
			driver.findElement(By.id("id_removeselected")).click();
		} catch (Exception e) {
			System.out.println("button not found:");
		}

		new WebDriverWait(driver, 60)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(buttonRemoveUserFilter)));
	}

	/**
	 * Throw IllegalStateException with user message
	 *
	 * @param message
	 */
	public void illegalStateException(String message) {
		throw new IllegalStateException(message);
		//throw new IllegalSta
	}

	/**
	 * Click using Webdriver AdavnceUserInterations API
	 *
	 * @param driver
	 * @param path
	 */
	public void actionBuilderClick(WebDriver driver, String path) {
		WebElement elm = driver.findElement(By.xpath(path));
		org.openqa.selenium.interactions.Actions builder = new org.openqa.selenium.interactions.Actions(driver);
		builder.click(elm).perform();
	}

	/**
	 * Wait max 60sec for specified 'number' of Windows to be present/open
	 *
	 * @param driver
	 * @param timeout
	 * @param numberOfWindows
	 */
	public void waitForNumberOfWindowsToEqual(WebDriver driver, int timeout, final int numberOfWindows) {
		new WebDriverWait(driver, timeout).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (driver.getWindowHandles().size() == numberOfWindows);
			}
		});
	}

	/**
	 * Verify Title of Window
	 *
	 * @param driver
	 * @param text
	 */
	public void verifyWindowTitle(WebDriver driver, String text, IsPresent ip) {
		String HandleBefore = driver.getWindowHandle();
		int i = 1;
		for (String handle : driver.getWindowHandles()) {
			System.out.println("window handle: " + handle);
			driver.switchTo().window(handle);
			if (i == driver.getWindowHandles().size()) {
				try {
					ip.isTitleContains(driver, text);
					if(text.contains("Agreement")) {
						ip.isGetTextContainsByXPATH(driver, "//a[contains(@href, '/app/uploads/2020/05/mapmarker-com-service-agreement-may-28-2020.pdf')]",
								"Mapmarker Online Agreement (May 2020)");
					}
					driver.close();
				} catch (Exception e) {
					System.out.println(text + " not found");
					driver.close();
					driver.switchTo().window(HandleBefore);
					throw e;
				}
			}
			i++;
		}
		driver.switchTo().window(HandleBefore);
	}

	/**
	 * Wait 60sec for Alert with expected TEXT is present
	 *
	 * @param driver
	 * @param timeout
	 * @param text
	 */
	public void waitForAlertToBeAccepted(WebDriver driver, int timeout, final String text) {
		Alert alert = new WebDriverWait(driver, 60).until(ExpectedConditions.alertIsPresent());
		if (alert.getText().contains(text)) {
			alert.accept();
		} else {
			String error = "Incorrect Alert present with Text as '" + alert.getText() + "'. " + "Expected text: '"
					+ text + "'";
			alert.dismiss();
			illegalStateException(error);
		}
	}

	/**
	 * Get Current NewYork Date in MM/dd/yyyy
	 *
	 * @param driver
	 * @return
	 */
	public String getCurrentNewYorkDate(WebDriver driver) {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("America/New_York"));
		String dt = (c.get(Calendar.MONTH) + 1) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR);
		System.out.println("Date in US->" + dt);
		return dt;
	}

	/**
	 * Get Next NewYork Date in MM/dd/yyyy
	 *
	 * @param driver
	 * @param currentDate
	 * @return
	 */
	public String getNextNewYorkDate(WebDriver driver, String currentDate) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try {
			c.setTime(sdf.parse(currentDate));
		} catch (ParseException ex) {
			Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
		}
		c.add(Calendar.DATE, 1); // number of days to add
		String nextDate = sdf.format(c.getTime()); // dt is now the new date
		System.out.println("New date->" + nextDate);
		return nextDate;
	}

	/**
	 *
	 * @param string
	 */
	public void copyContents(String string) {

		StringSelection stringSelection = new StringSelection("Somesh");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

		Robot robot = null;
		try {
			robot = new Robot();
			robot.delay(1000);
		} catch (AWTException ex) {
			Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
		}
		// robot.keyPress(KeyEvent.VK_ENTER);
		// robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	/**
	 * OnClick attribute of Input Element is handled for Chrome Browser by ROBOT
	 * functionality
	 *
	 * @param element
	 */
	public void robotclick(WebElement element) {
		Robot robot = null;
		try {
			robot = new Robot();
			robot.delay(1000);
		} catch (AWTException ex) {
			System.out.println("excptn:");
			// Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
		}
		robot.keyPress(KeyEvent.VK_CONTROL);
		element.click();
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}

	/**
	 * Verify specific TEXT is present in current URL
	 *
	 * @param driver
	 * @param textInUrl
	 */
	public void verifyCurrentUrlContains(WebDriver driver, String textInUrl) {
		if (!driver.getCurrentUrl().contains(textInUrl)) {
			illegalStateException(
					"Actual URL: '" + textInUrl + "' is not present " + "in Expected URL: " + driver.getCurrentUrl());
		}
	}

	/**
	 *
	 * @param name
	 * @return
	 */
	public String getFullName(String name) {
		return name + " " + name;
	}

	/**
	 * Type in Content Editable iframe
	 *
	 * @param driver
	 * @param iframeIndex
	 * @param textInIframe
	 */
	public void typeInContentEditableIframe(WebDriver driver, int iframeIndex, String textInIframe) {
		List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
		System.out.println("iframes count:" + iframes.size());
		int x = 1;
		loop: for (WebElement frame : iframes) {
			String iframeID = frame.getAttribute("id");
			System.out.println("Iframe ID: " + iframeID);
			if (x == iframeIndex) {
				driver.switchTo().frame(iframeID);
				break loop;
			}
			x++;
		}

		// Switch focus
		WebElement editableTxtArea = driver.switchTo().activeElement();
		editableTxtArea.sendKeys(Keys.chord(Keys.CONTROL, "a"), textInIframe);
		driver.switchTo().defaultContent();
	}

	/**
	 * Verify Date Present In Element Value field
	 *
	 * @param driver
	 * @param id
	 */
	public void verifyDatePresentInElementValue(WebDriver driver, By id) {
		String regex = "^(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((20)\\d\\d)$";
		int x = 1;

		do {
			String fetchdate = driver.findElement(id).getAttribute("value");
			if (!fetchdate.isEmpty()) {
				if (!Pattern.matches(regex, fetchdate)) {
					illegalStateException("Date (" + fetchdate + ") does not match the expected (mm/dd/yyyy) format");
				}
				break;
			}
			x++;
		} while (x < 2401);

		if (x > 2400) {
			illegalStateException("Timed out after 60 seconds waiting for presence of DATE located by ID: " + id);
		}
	}

	/**
	 * Read contents from a file and paste the contents in a text box field in
	 * website
	 *
	 * @param filePathName
	 * @param xPath
	 */
	public void readAndCopyContentsToTextField(WebDriver driver, String filePathName, String xPath) {
		try {
			FileInputStream fstream = new FileInputStream(filePathName);
			try (DataInputStream in = new DataInputStream(fstream)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String str;
				while ((str = br.readLine()) != null) {
					driver.findElement(By.xpath(xPath)).sendKeys(str);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	/**
	 * 
	 * @param driver
	 * @param iframeIndex
	 * @return
	 */
	public String getTextFromContentEditableIframe(WebDriver driver, int iframeIndex) {
		List<WebElement> iFrames = driver.findElements(By.tagName("iframe"));
		System.out.println("iFrames count:" + iFrames.size());
		int x = 1;
		for (WebElement iframe : iFrames) {
			String iframeID = iframe.getAttribute("id");
			System.out.println("Iframe ID: " + iframeID);
			if (x == iframeIndex) {
				driver.switchTo().frame(iframeID);
			}
			x++;
		}

		// Switch focus
		WebElement editableTxtArea = driver.switchTo().activeElement();
		String text = editableTxtArea.getText();
		driver.switchTo().defaultContent();
		return text;
	}

	// **NEW**

	/**
	 * 
	 * @param driver
	 * @param xpath
	 * @return
	 */
	public String getFirstSelectedOptionFromSelect(WebDriver driver, String xpath) {
		return new Select(driver.findElement(By.xpath(xpath))).getFirstSelectedOption().getText();
	}

	/**
	 * Uses jsLibrary to click on element on the page by XPATH
	 *
	 * @param driver
	 * @param menuXPATH
	 */
	public void clickByJavaScriptLibrary(WebDriver driver, String menuXPATH) {
		WebElement hiddenElement = driver.findElement(By.xpath(menuXPATH));
		JavascriptLibrary jsLib = new JavascriptLibrary();
		jsLib.callEmbeddedSelenium(driver, "triggerMouseEventAt", hiddenElement, "click", "0,0");
	}

	/**
	 * Click using Webdriver AdavnceUserInterations API
	 *
	 * @param driver
	 * @param path
	 */
	public void actionBuilderMoveToClick(WebDriver driver, String path) {
		WebElement elm = driver.findElement(By.xpath(path));
		org.openqa.selenium.interactions.Actions builder = new org.openqa.selenium.interactions.Actions(driver);
		builder.moveToElement(elm).click().perform();
	}

	/**
	 * 
	 * @param defaultDownloadPath
	 * @param fileName
	 * @return
	 */
	public boolean isFileDownloaded(String defaultDownloadPath, String fileName) {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		System.out.print("****Final FileName****:" + fileName + "\n");
		Path filePath = Paths.get(defaultDownloadPath, fileName);
		try {
			await().atMost(60, TimeUnit.SECONDS).ignoreExceptions().until(() -> filePath.toFile().exists());
		} catch (ConditionTimeoutException e) {
			illegalStateException("Unable to locate download file in filesystem until 1min: " + fileName);
		}
		File directory = new File(defaultDownloadPath);
		File[] filesList = null;
		int x = 0;
		LOOP: while (x < 10) {
			filesList = directory.listFiles();
			for (File file : filesList) {
				if (file.getName().contains(".crdownload") || file.getName().contains(".part")) {
					try {
						Thread.sleep(12000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					x++;
					continue LOOP;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param xlFilePath
	 * @param sheetName
	 * @param tableName
	 * @return
	 */
	public String[][] getTableArray(String xlFilePath, String sheetName, String tableName) {
		String[][] tabArray = null;
		try {
			Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
			Sheet sheet = workbook.getSheet(sheetName);
			int startRow, startCol, endRow, endCol, ci, cj, totalCols;
			Cell tableStart = sheet.findCell(tableName);
			startRow = tableStart.getRow();
			startCol = tableStart.getColumn();

			Cell tableEnd = sheet.findCell(tableName, startCol + 1, startRow + 1, 100, 64000, false);

			endRow = tableEnd.getRow();
			endCol = tableEnd.getColumn();
//			System.out.println("startRow=" + startRow + ", endRow=" + endRow + ", " + "startCol=" + startCol
//					+ ", endCol=" + endCol);
			totalRows = endRow - startRow - 1;
			totalCols = endCol - startCol - 1;
			tabArray = new String[totalRows][totalCols];
			ci = 0;

			for (int i = startRow + 1; i < endRow; i++, ci++) {
				cj = 0;
				for (int j = startCol + 1; j < endCol; j++, cj++) {
					tabArray[ci][cj] = sheet.getCell(j, i).getContents();
					//System.out.println("["+ci+"]["+cj+"]: " + tabArray[ci][cj] +"\n");
				}
			}
		} catch (Exception e) {
			System.out.println("error in getTableArray()");
		}

		return (tabArray);
	}
	
	/**
	 * 
	 * @return
	 */
	public String currentDateTime() {
		String pattern = "ddMMyyHHmmss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		return simpleDateFormat.format(new Date());
	}

	/**
	 *
	 * @return
	 */
	public String getMilliSeconds() {
		String pattern = "SSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		return simpleDateFormat.format(new Date());
	}

	public void waitForPageToBeReloaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, 60);
		try {
			wait.until(expectation);
		} catch (Throwable error) {
			// assertFalse("Timeout waiting for Page Load Request to complete.",true);
			illegalStateException("Timeout waiting for Page Load Request to complete.");
		}
	}

//	public boolean waitForJStoLoad(WebDriver driver) {
//
//	    WebDriverWait wait = new WebDriverWait(driver, 30);
//
//	    // wait for jQuery to load
//	    ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
//	      @Override
//	      public Boolean apply(WebDriver driver) {
//	        try {
//	          return ((Long)executeJavaScript("return jQuery.active") == 0);
//	        }
//	        catch (Exception e) {
//	          return true;
//	        }
//	      }
//	    };
//
//	    // wait for Javascript to load
//	    ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
//	      @Override
//	      public Boolean apply(WebDriver driver) {
//	        return executeJavaScript("return document.readyState")
//	            .toString().equals("complete");
//	      }
//	    };
//
//	  return wait.until(jQueryLoad) && wait.until(jsLoad);
//	}

	public void isjQueryLoaded(WebDriver driver) {
		System.out.println("Waiting for ready state complete");
		(new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				JavascriptExecutor js = (JavascriptExecutor) d;
				String readyState = js.executeScript("return document.readyState").toString();
				System.out.println("Ready State: " + readyState);
				return (Boolean) js.executeScript("return !!window.jQuery && window.jQuery.active == 0");
			}
		});
	}

	/**
	 *
	 * @param driver
	 * @param xpath
	 */
	public void waitTillSpinnerDisable(WebDriver driver, String xpath){
		IsPresent ip = new IsPresent();
		WebElement e = ip.findElementByXpath(driver, xpath);
		(new WebDriverWait(driver, 60)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver)
			{
				//System.out.println(e.getCssValue("display"));
				if(e.getCssValue("display").equalsIgnoreCase("none"))
				{
					return true;
				}
				return false;
			}
		});
	}

	public void abc(WebDriver driver) {
		new WebDriverWait(driver, 60).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver wdriver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		});
	}

	/**
	 * 
	 * @return
	 */
	public int getTotalRows() {
		return this.totalRows;
	}

	/**
	 * 
	 * @param defaultDownloadPath
	 */
	public void emptyDefaultDownloadPath(String defaultDownloadPath) {
		File file = new File(defaultDownloadPath);
		if(file.isDirectory()){
	         if(file.list().length > 0) { 
	            System.out.println("Directory is not empty!");
	            try {
					Files.walk(Paths.get(defaultDownloadPath)).filter(Files::isRegularFile).map(Path::toFile).forEach(File::delete);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
	         System.out.println("Directory is empty!");
		}
	}

	public void checkFieldsDraggedAutomatically() {
		// TODO Auto-generated method stub
	}

	public String getFirstName(String userID) {
		return userID.substring(0, userID.indexOf("+"));
	}

	public String getSecondName(String userID) {
		return userID.substring(userID.indexOf("+") + 1, userID.indexOf("@"));
	}

}
