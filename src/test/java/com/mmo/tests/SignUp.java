package com.mmo.tests;

import java.util.Iterator;

import com.mmo.util.EmailUtils;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;


public class SignUp extends BaseClass{
	
	static String[][] signUpFreeUSUserArray = new String[1][3];
	static String[][] signUpFreeNonUSUserArray = new String[1][3];
	static String[][] signUpPaid5kUserArray = new String[1][3];
	static String[][] signUpProfUserArray = new String[1][3];
	static String[][] signUpGeoTaxUserArray = new String[1][3];

	public Actions a = new Actions();
	private static EmailUtils emailUtils;
	
	@DataProvider(name = "FreeUSUserDetails") 	
    public static Object[][] FreeUSUserDetails(ITestContext context) throws Exception {
        System.out.println("init FreeUSUserDetails");
		return (signUpFreeUSUserArray);
    }
	
    @DataProvider(name = "FreeNonUSUserDetails") 	
    public static Object[][] FreeNonUSUserDetails(ITestContext context) throws Exception {
        System.out.println("init FreeNonUSUserDetails");
		return (signUpFreeNonUSUserArray);
    }
	
    @DataProvider(name = "Paid5kUserDetails") 	
    public static Object[][] Paid5kUserDetails(ITestContext context) throws Exception {
        System.out.println("init Paid5kUserDetails");
		return (signUpPaid5kUserArray);
    }
	
    @DataProvider(name = "ProfUserDetails") 	
    public static Object[][] ProfUserDetails(ITestContext context) throws Exception {
        System.out.println("init ProfUserDetails");
		return (signUpProfUserArray);
    }

	@DataProvider(name = "GeoTaxUserDetails")
	public static Object[][] GeoTaxUserDetails(ITestContext context) throws Exception {
		System.out.println("init GeoTaxUserDetails");
		signUpGeoTaxUserArray[0][0] = "mmoAutomated+GeoTaxUsernull@gmail.com";
		signUpGeoTaxUserArray[0][1] = "mmoAutomated";
		signUpGeoTaxUserArray[0][2] = "GeoTaxUsernull";
		return (signUpGeoTaxUserArray);
	}
	
    //@BeforeClass(groups = {"prerequisite"})
	public static void connectToEmail() {
		try {
			emailUtils = new EmailUtils("mmoautomated@gmail.com", "Precisely@123",
					"smtp.gmail.com", EmailUtils.EmailFolder.STARTUSINGMMO);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	//@Test
	public void testSignUpFreeUSUserAndCompleteEmailRegistration() throws Exception {
		a.navigateToHomePage();
		signUpFreeUSUserArray =  a.signUpUser("free", "US", "N");
		Reporter.log("signUpFreeUSUserID: " + signUpFreeUSUserArray[0][0], true);
		Reporter.log("signUpFreeUSUserFirstName: " + signUpFreeUSUserArray[0][1], true);
		Reporter.log("signUpFreeUSUserSecondName: " + signUpFreeUSUserArray[0][2], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", signUpFreeUSUserArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(signUpFreeUSUserArray[0][0], signUpFreeUSUserArray[0][1], signUpFreeUSUserArray[0][2]  , claimTokenID);
	}
	
	//@Test
	public void testSignUpFreeNonUSUserAndCompleteEmailRegistration() throws Exception {
		a.navigateToHomePage();
		signUpFreeNonUSUserArray =  a.signUpUser("free", "NonUS", "N");
		Reporter.log("signUpFreeNonUSUserID: " + signUpFreeNonUSUserArray[0][0], true);
		Reporter.log("signUpFreeNonUSUserFirstName: " + signUpFreeNonUSUserArray[0][1], true);
		Reporter.log("signUpFreeNonUSUserSecondName: " + signUpFreeNonUSUserArray[0][2], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", signUpFreeNonUSUserArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(signUpFreeNonUSUserArray[0][0], signUpFreeNonUSUserArray[0][1], signUpFreeNonUSUserArray[0][2]  , claimTokenID);
	}
	
	//@Test
	public void testSignUpPaid5kUserAndCompleteEmailRegistration() throws Exception {
		a.navigateToHomePage();
		signUpPaid5kUserArray =  a.signUpUser("5k", "US", "N");
		Reporter.log("signUpPaid5kUserID: " + signUpPaid5kUserArray[0][0], true);
		Reporter.log("signUpPaid5kUserFirstName: " + signUpPaid5kUserArray[0][1], true);
		Reporter.log("signUpPaid5kUserSecondName: " + signUpPaid5kUserArray[0][2], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", signUpPaid5kUserArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(signUpPaid5kUserArray[0][0], signUpPaid5kUserArray[0][1], signUpPaid5kUserArray[0][2]  , claimTokenID);
	}
	
	//@Test
	public void testSignUpPaidProfUserAndCompleteEmailRegistration() throws Exception {
		a.navigateToHomePage();
		signUpProfUserArray =  a.signUpUser("prof", "US", "N");
		Reporter.log("signUpPaidProfUserID: " + signUpProfUserArray[0][0], true);
		Reporter.log("signUpPaidProfUserFirstName: " + signUpProfUserArray[0][0], true);
		Reporter.log("signUpPaidProfUserSecondName: " + signUpProfUserArray[0][0], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", signUpProfUserArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(signUpProfUserArray[0][0], signUpProfUserArray[0][1], signUpProfUserArray[0][2]  , claimTokenID);
	}

	@Test
	public void testSignUpGeoTaxUserAndCompleteEmailRegistration() throws Exception {
		a.navigateToGeoTaxPage();
		signUpGeoTaxUserArray =  a.signUpGeoTaxUser();
		Reporter.log("signUpGeoTaxUserID: " + signUpGeoTaxUserArray[0][0], true);
		Reporter.log("signUpGeoTaxUserFirstName: " + signUpGeoTaxUserArray[0][1], true);
		Reporter.log("signUpGeoTaxUserSecondName: " + signUpGeoTaxUserArray[0][2], true);
		Thread.sleep(10000);
		emailUtils = new EmailUtils("mmoautomated@gmail.com", "Precisely@123","smtp.gmail.com", EmailUtils.EmailFolder.STARTUSINGGEOTAX);
		String claimTokenID = emailUtils.getToken("You're ready to start using GeoTAX", signUpGeoTaxUserArray[0][0], EmailUtils.EmailFolder.STARTUSINGGEOTAX);
		a.completeRegistration(signUpGeoTaxUserArray[0][0], signUpGeoTaxUserArray[0][1], signUpGeoTaxUserArray[0][2]  , claimTokenID);
	}

	@AfterClass(groups = {"prerequisite"})
	public void testLogout() throws Exception {
		//a.logOut();
	}

}
