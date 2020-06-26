package com.mmo.util;

import java.util.Date;

import org.openqa.selenium.By;

import com.mmo.pages.CreateUserPage;
import com.mmo.pages.DashboardPage;
import com.mmo.pages.EmailPage;
import com.mmo.pages.FooterPage;
import com.mmo.pages.HomePage;
import com.mmo.pages.JobPage;
import com.mmo.pages.LoginPage;
import com.mmo.pages.SignUpPage;

public class Actions extends BaseClass{

	Date now = new Date();
	
	/**
	 * Login
	 *
	 * @param user
	 */
	public void verifyLoginPage() {
		LoginPage lp = new LoginPage();
		lp.verifyLoginPage();
	}
	
	/**
	 * Login
	 *
	 * @param user
	 */
	public void login(String user) {
		LoginPage lp = new LoginPage();
		lp.attemptLogin(user);
	}

	/**
	 * 
	 */
	public void home() {
		FooterPage fp = new FooterPage();
		fp.verifyFooter();
		
		HomePage hp = new HomePage();
		hp.verifyHomePage();
	}

	/**
	 * 
	 */
	public String[] signUpUser(String plan, String region) {
		SignUpPage su = new SignUpPage();
		su.signUpUser(plan, region);
		return su.getUserDetails();
	}
	
	/**
     * Navigate to Email Page
     */
    public void navigateToEmail() {
    	//driver.get("https://mail.google.com/");
    	driver.get("https://mail.google.com/mail/?ui=html");
		ip.isTitlePresent(driver, "Gmail");		 
    }
    
    /**
     * Navigate to Login Page
     */
    public void navigateToLogin() {
    	ip.isElementClickableByXpath(driver, xpv.getTokenValue("homePageSignIn"), 60);
    	driver.findElement(By.xpath(xpv.getTokenValue("homePageSignIn"))).click();
        ip.isElementClickableByXpath(driver, xpv.getTokenValue("signInUserName"), 60);
    }
    
    /**
     * Navigate to Dashboard Page
     */
    public void navigateToDashboard() {
    	ip.isElementClickableByXpath(driver, "//a[contains(text(),'Dashboard')]", 60);
    	u.clickByJavaScript(driver, "//a[contains(text(),'Dashboard')]");
    	ip.isTextPresentByXPATH(driver, "//h1", "MapMarker Dashboard");    	
    }
    
    /**
     * Navigate to Upload File Page
     */
    public void navigateToUploadFile() {
    	ip.isElementClickableByXpath(driver, "//button[@id='btnUploadFile']", 60);
    	u.clickByJavaScript(driver, "//button[@id='btnUploadFile']");
		ip.isTextPresentByXPATH(driver, "//h1", "Step 1: Upload File"); 
    }
    
    /**
     * Navigate to Create User Page
     */
    public void navigateToCreateUser() {
    	ip.isElementClickableByXpath(driver, "xpath=//a[@id='btnCreateNewUser']", 60);
    	driver.findElement(By.xpath("xpath=//a[@id='btnCreateNewUser']")).click();
		ip.isTextPresentByXPATH(driver, "//h1", "Create New User"); 
    }
    
    
    /**
     * Navigate to Billing & Plans Page
     */
    public void navigateToBillingPlan() {
    	ip.isElementClickableByXpath(driver, "//li[@id='userContextMenuLi']/a/div/div", 60);
    	u.clickByJavaScript(driver, "//a[contains(text(),'Billing & Plans')]");
    	ip.isTextPresentByXPATH(driver, "//h1", "Billing & Plans");
    	u.verifyCurrentUrlContains(driver, "billing");    	
    }
    
    
    public void createUser(String userRole) {	
    	CreateUserPage cup = new CreateUserPage();
    	cup.createUser(userRole);
    }
    
    public void verifyCreatedUser() {	
    	CreateUserPage cup = new CreateUserPage();
    	cup.verifyUserCreated();
    }
    
    
    public String uploadFileConfigureAndStartJob(String inputFileName, String geocodingType, String autoDrag, String dragColumns, String dropFieldsToGeocode, String outputFields,
			String outputFormat, String coordSystem, String country, String matchMode) {
    	JobPage jp = new JobPage();
		jp.uploadFileConfigureAndStartJob(inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode, outputFields,
				outputFormat, coordSystem, country, matchMode);
		return jp.getOutputFileName();
    }
    
    /**
	 * 
	 * @param inputFileName
	 * @param geocodingType
	 * @param expectedMessage
	 */
	public void uploadIncorrectFilesAndCheckValidations(String inputFileName, String geocodingType,
			String expectedMessage) {
		JobPage jp = new JobPage();
		jp.uploadIncorrectFilesAndCheckValidations(inputFileName, geocodingType, expectedMessage);
	}
    
    public void waitforJobToGetComplete(String outputFileName) {
    	JobPage jp = new JobPage();
		jp.waitforJobToGetComplete(outputFileName);
    }
    
    public void getJobDetails(String outputFilename) {
    	JobPage jp = new JobPage();
		jp.getJobDetails(outputFilename);
    }
    
    public void downloadOutputFileAndCompare(String outputFileName,String outFileFormat) {
    	JobPage jp = new JobPage();
		jp.downloadJobOutputFileAndCompare(outputFileName, outFileFormat);
    }
    
    /**
	 * 
	 */
//	public void completeRegisteration(String signUpUserID, String signUpFirstName, String signUpSecondName, String claimTokenID) {
//		EmailPage ep = new EmailPage();
//		ep.completeRegisteration(signUpUserID, signUpFirstName, signUpSecondName, claimTokenID);
//	}

	public void completeRegisteration(String signUpFreeUSUser, String signUpFreeUSUserFirstName, String signUpFreeUSUserSecondName, String claimTokenID) {
		EmailPage ep = new EmailPage();
		ep.completeRegisteration(signUpFreeUSUser, signUpFreeUSUserFirstName, signUpFreeUSUserSecondName, claimTokenID);
	}
	
	/**
	 * 
	 */
	public void verifyDashboard() {
		//FooterPage fp = new FooterPage();
		//fp.verifyFooter();
		
		DashboardPage dp = new DashboardPage();
		dp.verifyDashboard();
	}
	
	/**
	 * 
	 * @param defaultTemplates
	 */
	public void downloadAllDefaultTemplates(String[] defaultTemplates) {
		DashboardPage dp = new DashboardPage();
		dp.downloadAllDefaultTemplates(defaultTemplates);
	}

	/**
	 * User logs out
	 */
	public void logOut() {
		u.clickByJavaScript(driver, xpv.getTokenValue("linkToLogOut"));
		ip.isElementClickableByXpath(driver, xpv.getTokenValue("signInUserName"), 60);
		
//		if(env.equalsIgnoreCase("qa"))
//        {
//			driver.getCurrentUrl().contains(xpv.getTokenValue("qaURLContains"));      	
//        } else if(env.equalsIgnoreCase("ppd"))
//        {
//        	driver.getCurrentUrl().contains(xpv.getTokenValue("ppdURLContains"));
//        } else {
//        	driver.getCurrentUrl().contains(xpv.getTokenValue("prodURLContains"));
//        } 
		
	}

	public void viewJobDetails(String outFileName) {
		JobPage jp = new JobPage();
		jp.viewJobDetails(outFileName);
	}

}
