package com.mmo.util;

import java.util.Date;

import org.openqa.selenium.By;

import com.mmo.pages.DashboardPage;
import com.mmo.pages.EmailPage;
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
	public void login(String user) {
		LoginPage lp = new LoginPage();
		lp.attemptLogin(user);
	}

	/**
	 * 
	 */
	public void home() {
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
    	driver.get("https://mail.google.com/");
		ip.isTitlePresent(driver, "Gmail");		 
    }
    
    /**
     * Navigate to Login Page
     */
    public void navigateToLogin() {
    	driver.findElement(By.xpath(xpv.getTokenValue("homePageSignIn"))).click();
        ip.isElementClickableByXpath(driver, xpv.getTokenValue("signInUserName"), 60);
    }
    
    /**
     * Navigate to Dashboard Page
     */
    public void navigateToDashboard() {
    	ip.isElementClickableByXpath(driver, "//a[contains(text(),'Dashboard')]", 60);
    	u.clickByJavaScript(driver, "//a[contains(text(),'Dashboard')]");
    	//driver.findElement(By.xpath("//a[contains(text(),'Dashboard')]")).click();
		ip.isElementClickableByXpath(driver, "//button[@id='btnUploadFile']", 60);		 
    }
    
    /**
     * Navigate to Upload File Page
     */
    public void navigateToUploadFile() {
    	ip.isElementClickableByXpath(driver, "//button[@id='btnUploadFile']", 60);
    	driver.findElement(By.xpath("//button[@id='btnUploadFile']")).click();
		ip.isTextPresentByXPATH(driver, "//h1", "Step 1: Upload File"); 
    }
    
    
    public void uploadFileConfigureAndStartJob(String inputFileName, String dragColumns, String dropFieldsToGeocode, String outputFields,
			String outputFormat, String country, String matchMode) {
    	JobPage jp = new JobPage();
		jp.uploadFileConfigureAndStartJob(inputFileName, dragColumns, dropFieldsToGeocode, outputFields,
				outputFormat, country, matchMode);
    }
    
    public void getJobDetailsAfterCompletion(String outputFilename) {
    	JobPage jp = new JobPage();
		jp.getJobDetailsAfterCompletion(outputFilename);
    }
    
    /**
	 * 
	 */
	public void emailLogin() {
		EmailPage ep = new EmailPage();
		ep.emailLogin();
	}
	
	/**
	 * 
	 */
	public void signUpUserCompleteRegisteration(String signUpUserID, String signUpFirstName, String signUpSecondName) {
		EmailPage ep = new EmailPage();
		ep.signUpUserCompleteRegisteration(signUpUserID, signUpFirstName, signUpSecondName);
	}
	
	/**
	 * 
	 */
	public void verifyDashboard() {
		DashboardPage dp = new DashboardPage();
		dp.verifyDashboard();
	}
	
	/**
	 */
	public void downloadDefaultTemplatesAndVerify(String[] defaultTemplates) {
		DashboardPage dp = new DashboardPage();
		dp.downloadDefaultTemplatesAndVerify(defaultTemplates);
	}

	/**
	 * User logs out
	 */
	public void logOut() {
		u.clickByJavaScript(driver, xpv.getTokenValue("linkToLogOut"));
		ip.isElementClickableByXpath(driver, xpv.getTokenValue("signInUserName"), 60);
		if (!driver.getCurrentUrl().contains(xpv.getTokenValue("qaURLContains"))) {
			u.illegalStateException("Current URL is not as expected.  Current URL: " + driver.getCurrentUrl());
		}       
	}
}
