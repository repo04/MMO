package com.mmo.util;

import com.mmo.pages.*;
import org.openqa.selenium.By;

import java.util.Date;

public class Actions extends BaseClass{

	Date now = new Date();

	/**
	 *
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
	public void verifyHomePage() {
		HomePage hp = new HomePage();
		hp.verifyHomePage();
	}

	public void verifyFooters() {
		FooterPage fp = new FooterPage();
		fp.verifyFooters();
	}

	public void verifyLoginPageFooters() {
		FooterPage fp = new FooterPage();
		fp.verifyLoginPageFooters();
	}

	/**
	 *
	 * @param plan
	 * @param region
	 * @return
	 */
	public String signUpUser(String plan, String region, String addProductFlow) {
		SignUpPage su = new SignUpPage();
		su.signUpUser(plan, region, addProductFlow);
		return su.getUserDetails();
	}

	public String signUpUserThroughAPI(String plan, String region, String token) {
		SignUpPage su = new SignUpPage();
		su.signUpUserThroughAPI(plan, region, token);
		return su.getUserDetails();
	}

	public String getSubId() {
		SignUpPage su = new SignUpPage();
		return su.getSubscriptionID();
	}

	public void navigateToSignUpForAddProductFlow(String plan, String addProductFlow) {
		SignUpPage su = new SignUpPage();
		su.navigateToSignUpForAddProductFlow(plan, addProductFlow);
	}

	/**
	 *
	 * @return
	 */
	public String signUpGeoTaxUser() {
		SignUpPage su = new SignUpPage();
		su.signUpGeoTaxUser();
		return su.getUserDetails();
	}
	
	/**
     * Navigate to Email Page
     */
    public void navigateToEmail() {
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
		ip.isGetTextContainsByXPATH(driver, "//h1", "MapMarker Dashboard");
		u.waitTillSpinnerDisable(driver, "//div[starts-with(@class,'spinner-sample')]");
	}
    
    /**
     * Navigate to Upload File Page
     */
    public void navigateToUploadFile() {
    	ip.isElementClickableByXpath(driver, "//button[@id='btnUploadFile']", 60);
    	u.clickByJavaScript(driver, "//button[@id='btnUploadFile']");
		ip.isGetTextContainsByXPATH(driver, "//h1", "Step 1: Upload File");
    }
    
    /**
     * Navigate to Create User Page
     */
    public void navigateToCreateUser() {
    	ip.isElementClickableByXpath(driver, "//a[@id='btnCreateNewUser']", 60);
    	driver.findElement(By.xpath("//a[@id='btnCreateNewUser']")).click();
		ip.isGetTextContainsByXPATH(driver, "//h1", "Create New User");
    }

    /**
     * Navigate to Billing & Plans Page
     */
    public void navigateToBillingPlan() {
    	ip.isElementClickableByXpath(driver, "//li[@id='userContextMenuLi']/a/div/div", 60);
    	u.clickByJavaScript(driver, "//a[contains(text(),'Billing & Plans')]");
		u.waitTillSpinnerDisable(driver, "//div[starts-with(@class,'spinner-sample')]");
		ip.isGetTextContainsByXPATH(driver, "//h1", "Billing & Plans");
    	u.verifyCurrentUrlContains(driver, "billing");    	
    }

	/**
	 *
	 */
	public void navigateToUsers() {
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'Users')]", 60);
		driver.findElement(By.xpath("//a[contains(text(),'Users')]")).click();
		ip.isGetTextContainsByXPATH(driver, "//h1", "Users");
		ip.isElementClickableByXpath(driver, "//a[contains(text(),'Create New User')]", 60);
	}

	/**
	 *
	 * @param userRole
	 * @return
	 */
    public String createUser(String userType, String userRole) {
    	UsersPage up = new UsersPage();
		up.createUser(userType, userRole);
    	return up.getSubUserDetails();
    }

	public String createUserThroughAPI(String userType, String userRole, String bearerToken, String subID) {
		UsersPage up = new UsersPage();
		up.createUserThroughAPI(userType, userRole, bearerToken, subID);
		return up.getSubUserDetails();
	}

	public void deleteUser(String userID) {
		UsersPage up = new UsersPage();
		up.deleteUser(userID);
	}

	/**
	 *
	 * @param userID
	 * @param userFirstName
	 * @param userSecondName
	 * @param role
	 */
	public void verifyUserDetailInUsersList(String userID, String userFirstName, String userSecondName, String role) {
    	UsersPage up = new UsersPage();
		up.verifyUserDetailInUsersList(userID, userFirstName, userSecondName, role);
    }

    public String uploadFileConfigureAndStartJob(String secondName, String inputFileName, String geocodingType, String autoDrag, String dragColumns, String dropFieldsToGeocode, String outputFields,
			String outputFormat, String coordSystem, String country, String matchMode, String totalRecords, String advanceGeocoding, String multiMatch) {
    	JobPage jp = new JobPage();
		jp.uploadFileConfigureAndStartJob(secondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode, outputFields,
				outputFormat, coordSystem, country, matchMode, totalRecords, advanceGeocoding, multiMatch);
		return jp.getOutputFileName();
    }
    
    /**
	 * 
	 * @param inputFileName
	 * @param geocodingType
	 * @param expectedMessage
	 */
	public void uploadIncorrectFilesAndCheckValidations(String loginId, String inputFileName, String geocodingType, String expectedMessage) {
		JobPage jp = new JobPage();
		jp.uploadIncorrectFilesAndCheckValidations(loginId, inputFileName, geocodingType, expectedMessage);
	}
    
    public void waitforJobToGetComplete(String userSecondName, String outputFileName, long ms) {
    	JobPage jp = new JobPage();
		jp.waitForJobToGetComplete(userSecondName, outputFileName, ms);
    }
    
    public void getJobDetails(String outputFilename) {
    	JobPage jp = new JobPage();
		jp.getJobDetails(outputFilename);
    }

	public void downloadOutputVerifyExtensionsAndDataTypeLength(String secondName, String outputFileName,String outFileFormat) {
    	JobPage jp = new JobPage();
		jp.downloadOutputVerifyExtensionsAndDataTypeLength(secondName, outputFileName, outFileFormat);
    }

	/**
	 *
	 * @param signUpFreeUSUser
	 * @param signUpFreeUSUserFirstName
	 * @param signUpFreeUSUserSecondName
	 * @param claimTokenID
	 */
	public void completeRegistration(String signUpFreeUSUser, String signUpFreeUSUserFirstName, String signUpFreeUSUserSecondName, String claimTokenID) {
		EmailPage ep = new EmailPage();
		ep.completeRegistration(signUpFreeUSUser, signUpFreeUSUserFirstName, signUpFreeUSUserSecondName, claimTokenID);
	}
	
	/**
	 * 
	 */
	public void verifyDashboard(String userFirstName, String userSecondName) {
		DashboardPage dp = new DashboardPage();
		dp.verifyDashboard(userFirstName, userSecondName);
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
	}

	public void verifyJobDetails(String userSecondName, String inputFileName, String geocodingType,
								 String outputFields, String outputFormat, String coordSystem, String country,
								 String matchMode, String totalRecords, String advanceGeocoding, String multiMatch, String outFileName) {
		JobPage jp = new JobPage();
		jp.verifyJobDetails(userSecondName, inputFileName, geocodingType, outputFields, outputFormat,
				coordSystem, country, matchMode, totalRecords, advanceGeocoding, multiMatch, outFileName);
	}

	public void verifyJobsShownToUser(String userSecondName, String outFileName) {
		JobPage jp = new JobPage();
		jp.verifyJobsShownToUser(userSecondName, outFileName);
	}

	public void deleteJob(String userType, String outFileName) {
		JobPage jp = new JobPage();
		jp.deleteJob(userType, outFileName);
	}

	public void navigateToHomePage() {

		driver.get("https://" + xpv.getTokenValue(envValue+"URL"));
		ip.isTitlePresent(driver, "MapMarker");
	}

	public void navigateToGeoTaxPage() {
		driver.get("https://geotax-qa.li.precisely.services");
		ip.isTitlePresent(driver, "GeoTAX");
	}

	public void enterLoginDetailsOnly(String userID) {
		LoginPage lp = new LoginPage();
		lp.enterLoginDetailsOnly(userID);
	}

	public void navigateToHomeAndVerifyAddProductFlowInitiated() {
		ip.isTitlePresent(driver, "MapMarker");
		ip.isURLContains(driver, "addproductscroll");
	}

	public void changePlan(String userID, String userFirstName, String userSecondName, String plan) {
		BillingPage bp = new BillingPage();
		bp.changePlan(userID, userFirstName, userSecondName, plan);
	}

	public void verifyBillingPage(String userSecondName) {
		BillingPage bp = new BillingPage();
		bp.verifyBillingPage(userSecondName);
	}

    public void updateCard() {
		BillingPage bp = new BillingPage();
		bp.updateCard();
    }

	public void navigateToProfilePage() {
		ip.isElementPresentByXPATH(driver, "//a[contains(text(),'Profile')]");
		u.clickByJavaScript(driver, "//a[contains(text(),'Profile')]");
		ip.isGetTextContainsByXPATH(driver, "//h1", "Profile");
	}

	public void verifyProfilePage(String userID, String userFirstName, String userSecondName) {
		ProfilePage pp = new ProfilePage();
		pp.verifyProfilePage(userID, userFirstName, userSecondName);
	}
}
