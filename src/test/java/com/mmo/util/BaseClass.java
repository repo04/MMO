package com.mmo.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import javax.mail.Message;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//@Listeners({TestNGCustomReport.class})
public class BaseClass {

	public static XpathValues xpv;
    public static WebDriver driver;
    public static String currentURL;
    public static File directory = new File(".");
    public static String defaultDownloadPath;
    public static DataProviderUtility dp = new DataProviderUtility();
    public static String browserValue;
    public static String testValue;
    public static String envValue;
    public static Utility u = new Utility();
    public IsPresent ip = new IsPresent();
    public static SoftAssert softAssert = new SoftAssert();
    public static EmailUtils emailUtils;
    public static String loginURL;
    public static Message[] Emails = null;
    public static ArrayList<String> failJobNames = new ArrayList<>();

    //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	

    /**
     * The annotated method will be run before any test method belonging to the
     * classes inside the <test> tag is run. Following parameter values are
     * received through 'Run Target' specified in build.xml. TestNG allows to
     * perform sophisticated groupings of test methods which is called from XML
     * file
     *
     * @param env
     * @param browser
     * @param test
     *
     * @throws Exception
     */
    @BeforeTest(groups = {"prerequisite"})
    @Parameters({"env", "browser", "test"})
    public void setUp(String env, String browser, String test) throws Exception {

    	envValue = env;
    	browserValue = browser;
        testValue = test;
        Actions a = new Actions();
        
        xpv = new XpathValues("xPathAccountProperty");
        System.out.println("env: " + envValue);
        System.out.println("browser: " + browserValue);
        System.out.println("test: " + testValue);
        defaultDownloadPath = directory.getCanonicalPath() + File.separator + "data" + File.separator + "downloadedFiles";

        emailUtils =  new EmailUtils("mmoautomated@gmail.com", "Precisely@123","smtp.gmail.com", EmailUtils.EmailFolder.STARTUSINGMMO);

        if(envValue.equalsIgnoreCase("qa"))
        {
            loginURL = "login-qa.saas.precisely.services";
        } else if(envValue.equalsIgnoreCase("ppd"))
        {
            loginURL = "login-ppd.saas.precisely.com";
        } else {
            loginURL = "login.saas.precisely.com";
        }

        switch (browser) {
            case "chrome":
                String chromDrvrPath = directory.getCanonicalPath() + File.separator + "lib" + File.separator;
                System.setProperty("webdriver.chrome.driver", chromDrvrPath + "chromedriver_win32" + File.separator + "chromedriver.exe");
                
                // Setting new download directory path
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("download.default_directory", defaultDownloadPath);
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-extensions");
                options.addArguments("--disable-gpu");
                options.addArguments("--dns-prefetch-disable");
                options.setExperimentalOption("prefs", prefs);
                driver = new ChromeDriver(options);
                Reporter.log("Browser: " + browser + "<br/>");
                break;
            case "ie":
            	String ieDrvrPath = directory.getCanonicalPath() + File.separator + "lib" + File.separator;
            	System.setProperty("webdriver.ie.driver", ieDrvrPath + "IEDriverServer_Win32_3.150.1" + File.separator + "IEDriverServer.exe");
            	driver = new InternetExplorerDriver();
            	driver.manage().window().maximize();
            	Reporter.log("Browser: " + browser + "<br/>");
            	break;
            default:
            	String ffDrvrPath = directory.getCanonicalPath() + File.separator + "lib" + File.separator;
            	System.setProperty("webdriver.gecko.driver", ffDrvrPath + "geckodriver-v0.25.0-win64" + File.separator + "geckodriver.exe");
            	FirefoxProfile profile = new FirefoxProfile();
            	profile.setPreference("browser.download.dir", defaultDownloadPath);
            	profile.setPreference("browser.download.folderList",2);
            	profile.setPreference("browser.download.manager.showWhenStarting", false);
        		profile.setPreference("browser.helperApps.neverAsk.saveToDisk","text/csv,application/x-msexcel,application/excel,"
        				+ "application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,"
        				+ "application/msword,application/xml,application/zip");
            	profile.setPreference("browser.helperApps.alwaysAsk.force", false);
            	profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
            	profile.setPreference("browser.download.manager.useWindow", false);
            	profile.setPreference("browser.download.manager.focusWhenStarting", false);
            	profile.setPreference("browser.download.manager.showAlertOnComplete", false);
            	profile.setPreference("browser.download.manager.closeWhenDone", true);
            	profile.setPreference("javascript.enabled", true);
            	FirefoxOptions option = new FirefoxOptions();
            	option.setProfile(profile);
            	driver = new FirefoxDriver(option);
            	driver.manage().window().maximize();
                Reporter.log("Browser: " + browser + "<br/>");
        }

        //a.navigateToHomePage();
    }

    /**
     * The annotated method will be run after all the test methods belonging to
     * the classes inside the <test> tag have run.
     *
     * @throws Exception
     */
    @AfterTest(alwaysRun = true, groups = {"prerequisite"})
    public void tearDown() throws Exception {
//        EmailUtils.storeClose();
//        driver.quit();
    }
}
