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
	
	static String signUpDetails[] = new String[3];
	static String[][] signUpFreeUSUserIDArray = new String[1][1];
	static String[][] signUpFreeUSUserFirstNameArray = new String[1][1];
	static String[][] signUpFreeUSUserSecondNameArray = new String[1][1];
	static String[][] signUpFreeNonUSUserIDArray = new String[1][1];
	static String[][] signUpFreeNonUSUserFirstNameArray = new String[1][1];
	static String[][] signUpFreeNonUSUserSecondNameArray = new String[1][1];
	static String[][] signUpPaid5kUserIDArray = new String[1][1];
	static String[][] signUpPaid5kUserFirstNameArray = new String[1][1];
	static String[][] signUpPaid5kUserSecondNameArray = new String[1][1];
	static String[][] signUpProfUserIDArray = new String[1][1];
	static String[][] signUpProfUserFirstNameArray = new String[1][1];
	static String[][] signUpProfUserSecondNameArray = new String[1][1];
	public Actions a = new Actions();
	private static EmailUtils emailUtils;
	
	@DataProvider(name = "FreeUSUserDetails") 	
    public static Iterator<Object[]> FreeUSUserDetails(ITestContext context) throws Exception {
        System.out.println("init FreeUSUserDetails");
        return DataProviderUtility.cartesianProviderFrom(signUpFreeUSUserIDArray, signUpFreeUSUserFirstNameArray, signUpFreeUSUserSecondNameArray);
    }
	
    @DataProvider(name = "FreeNonUSUserDetails") 	
    public static Iterator<Object[]> FreeNonUSUserDetails(ITestContext context) throws Exception {
        System.out.println("init FreeNonUSUserDetails");
        return DataProviderUtility.cartesianProviderFrom(signUpFreeNonUSUserIDArray, signUpFreeNonUSUserFirstNameArray, signUpFreeNonUSUserSecondNameArray);
    }
	
    @DataProvider(name = "Paid5kUserDetails") 	
    public static Iterator<Object[]> Paid5kUserDetails(ITestContext context) throws Exception {
        System.out.println("init Paid5kUserDetails");
        return DataProviderUtility.cartesianProviderFrom(signUpPaid5kUserIDArray, signUpPaid5kUserFirstNameArray, signUpPaid5kUserSecondNameArray);
    }
	
    @DataProvider(name = "ProfUserDetails") 	
    public static Iterator<Object[]> ProfUserDetails(ITestContext context) throws Exception {
        System.out.println("init ProfUserDetails");
        return DataProviderUtility.cartesianProviderFrom(signUpProfUserIDArray, signUpProfUserFirstNameArray, signUpProfUserSecondNameArray);
    }
	
    @BeforeClass(groups = {"prerequisite"})
	public static void connectToEmail() {
		try {
			emailUtils = new EmailUtils("autommopb@gmail.com", "Pitney@123",
					"smtp.gmail.com", EmailUtils.EmailFolder.INBOX);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	//@Test
	public void testSignUpFreeUSUserAndCompleteEmailRegisteration() throws Exception {
		signUpDetails =  a.signUpUser("free", "US");
		signUpFreeUSUserIDArray[0][0] = signUpDetails[0];
		signUpFreeUSUserFirstNameArray[0][0] = signUpDetails[1];
		signUpFreeUSUserSecondNameArray[0][0] = signUpDetails[2];
		Reporter.log("signUpFreeUSUserID: " + signUpFreeUSUserIDArray[0][0], true);
		Reporter.log("signUpFreeUSUserFirstName: " + signUpFreeUSUserFirstNameArray[0][0], true);
		Reporter.log("signUpFreeUSUserSecondName: " + signUpFreeUSUserSecondNameArray[0][0], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", signUpFreeUSUserIDArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegisteration(signUpFreeUSUserIDArray[0][0], signUpFreeUSUserFirstNameArray[0][0], signUpFreeUSUserSecondNameArray[0][0]  , claimTokenID);
	}
	
	//@Test
	public void testSignUpFreeNonUSUser() throws Exception {
		signUpFreeNonUSUserIDArray[0][0] = signUpDetails[0];
		signUpFreeNonUSUserFirstNameArray[0][0] = signUpDetails[1];
		signUpFreeNonUSUserSecondNameArray[0][0] = signUpDetails[2];
		Reporter.log("signUpFreeNonUSUserID: " + signUpFreeNonUSUserIDArray[0][0], true);
		Reporter.log("signUpFreeNonUSUserFirstName: " + signUpFreeNonUSUserFirstNameArray[0][0], true);
		Reporter.log("signUpFreeNonUSUserSecondName: " + signUpFreeNonUSUserSecondNameArray[0][0], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", signUpFreeNonUSUserIDArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegisteration(signUpFreeNonUSUserIDArray[0][0], signUpFreeNonUSUserFirstNameArray[0][0], signUpFreeNonUSUserSecondNameArray[0][0]  , claimTokenID);
	}
	
	@Test
	public void testSignUpPaid5kUser() throws Exception {
		signUpDetails =  a.signUpUser("5k", "US");
		signUpPaid5kUserIDArray[0][0] = signUpDetails[0];
		signUpPaid5kUserFirstNameArray[0][0] = signUpDetails[1];
		signUpPaid5kUserSecondNameArray[0][0] = signUpDetails[2];
		Reporter.log("signUpPaid5kUserID: " + signUpPaid5kUserIDArray[0][0], true);
		Reporter.log("signUpPaid5kUserFirstName: " + signUpPaid5kUserFirstNameArray[0][0], true);
		Reporter.log("signUpPaid5kUserSecondName: " + signUpPaid5kUserSecondNameArray[0][0], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", signUpPaid5kUserIDArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegisteration(signUpPaid5kUserIDArray[0][0], signUpPaid5kUserFirstNameArray[0][0], signUpPaid5kUserSecondNameArray[0][0]  , claimTokenID);
	}
	
	//@Test
	public void testSignUpPaidProfUser() throws Exception {
		signUpDetails =  a.signUpUser("prof", "US");
		signUpProfUserIDArray[0][0] = signUpDetails[0];
		signUpProfUserFirstNameArray[0][0] = signUpDetails[1];
		signUpProfUserSecondNameArray[0][0] = signUpDetails[2];
		Reporter.log("signUpPaidProfUserID: " + signUpProfUserIDArray[0][0], true);
		Reporter.log("signUpPaidProfUserFirstName: " + signUpProfUserFirstNameArray[0][0], true);
		Reporter.log("signUpPaidProfUserSecondName: " + signUpProfUserSecondNameArray[0][0], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", signUpProfUserIDArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegisteration(signUpProfUserIDArray[0][0], signUpProfUserFirstNameArray[0][0], signUpProfUserSecondNameArray[0][0]  , claimTokenID);
	}

	@AfterClass(groups = {"prerequisite"})
	public void testLogout() throws Exception {
		//a.logOut();
	}

}
