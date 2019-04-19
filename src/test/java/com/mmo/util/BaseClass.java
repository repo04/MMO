package com.mmo.util;

import java.io.File;

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
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

@Listeners({TestNGCustomReport.class})
public class BaseClass {

	public static XpathValues xpv;
    public static WebDriver driver;
    public static String currentURL;
    public static File directory = new File(".");
    public static String defaultDownloadPath;
    public static DataProviderUtility dp = new DataProviderUtility();
    public static String browser;
    public String test;
    public String env;
    public Utility u = new Utility();
    public IsPresent ip = new IsPresent();
    public static SoftAssert softAssert = new SoftAssert();
	
    
    //driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	

    /**
     * The annotated method will be run before any test method belonging to the
     * classes inside the <test> tag is run. Following parameter values are
     * received through 'Run Target' specified in build.xml. TestNG allows to
     * perform sophisticated groupings of test methods which is called from XML
     * file
     *
     * @param url
     * @param program
     * @param environment
     * @param browser
     * @param os
     * @param test
     * @throws Exception
     */
    @BeforeTest(groups = {"prerequisite"})
    @Parameters({"env", "browser","test"})
    public void setUp(String env, String browser, String test) throws Exception {

    	this.env = env;
    	this.browser = browser;
        this.test = test; 
        
        xpv = new XpathValues("xPathAccountProperty");
        System.out.println("env: " + this.env);
        System.out.println("browser: " + this.browser);
        System.out.println("test: " + this.test);

        switch (browser) {
            case "chrome":
                String chromDrvrPath = directory.getCanonicalPath() + File.separator + "lib" + File.separator;
                System.setProperty("webdriver.chrome.driver", chromDrvrPath + "chromedriver_win32" + File.separator + "chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-extensions");
                driver = new ChromeDriver(options);
                Reporter.log("Browser: " + browser);
                break;
            case "ie":
            	String ieDrvrPath = directory.getCanonicalPath() + File.separator + "lib" + File.separator;
            	System.setProperty("webdriver.ie.driver", ieDrvrPath + "IEDriverServer_Win32_3.14.0" + File.separator + "IEDriverServer.exe");
            	driver = new InternetExplorerDriver();
            	driver.manage().window().maximize();
            	Reporter.log("Browser: " + browser);
            	break;
            default:
            	String ffDrvrPath = directory.getCanonicalPath() + File.separator + "lib" + File.separator;
            	defaultDownloadPath = directory.getCanonicalPath() + File.separator + "data" + File.separator + "defaultTemplates";
            	System.setProperty("webdriver.gecko.driver", ffDrvrPath + "geckodriver-v0.24.0-win64" + File.separator + "geckodriver.exe");
            	//System.setProperty("webdriver.gecko.driver", ffDrvrPath + "geckodriver-v0.19.0-win64" + File.separator + "geckodriver.exe");
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
            	FirefoxOptions option = new FirefoxOptions();
            	option.setProfile(profile);
            	driver = new FirefoxDriver(option);
                driver.manage().window().maximize();
                Reporter.log("Browser: " + browser);                
        }
        
        if(this.env.equalsIgnoreCase("qa"))
        {
        	System.out.print("****OPEN QA URL****" + "\n");
        	driver.get("https://" + xpv.getTokenValue("qaURL"));        	
        } else if(this.env.equalsIgnoreCase("ppd"))
        {
        	System.out.print("****OPEN PPD URL****");
        	driver.get(xpv.getTokenValue("ppdURL"));
        } else {
        	System.out.print("****OPEN PROD URL****");
        	driver.get(xpv.getTokenValue("prodURL"));
        }        
    }

    /**
     * The annotated method will be run after all the test methods belonging to
     * the classes inside the <test> tag have run.
     *
     * @throws Exception
     */
    @AfterTest(alwaysRun = true, groups = {"prerequisite"})
    public void tearDown() throws Exception {
        //driver.quit();
    }
}
