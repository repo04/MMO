package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.EmailUtils;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class SignUp extends BaseClass{
	
	static String[][] freeUSUserArray = new String[1][3];
	static String[][] freeNonUSUserArray = new String[1][3];
	static String[][] paid5kUserArray = new String[1][3];
	static String[][] profUserArray = new String[1][3];
	private Actions a = new Actions();

	@DataProvider(name = "FreeUSUserDetails")
    public static Object[][] FreeUSUserDetails(ITestContext context) throws Exception {
        System.out.println("init FreeUSUserDetails");
		freeUSUserArray[0][0] = "mmoAutomated+FreeUS080720103045@gmail.com";
		freeUSUserArray[0][1] = "mmoAutomated";
		freeUSUserArray[0][2] = "FreeUS080720103045";
		return (freeUSUserArray);
    }
	
    @DataProvider(name = "FreeNonUSUserDetails") 	
    public static Object[][] FreeNonUSUserDetails(ITestContext context) throws Exception {
        System.out.println("init FreeNonUSUserDetails");
		freeNonUSUserArray[0][0] = "mmoAutomated+FreeNonUS020720140444@gmail.com";
		freeNonUSUserArray[0][1] = "mmoAutomated";
		freeNonUSUserArray[0][2] = "FreeNonUS020720140444";
		return (freeNonUSUserArray);
    }
	
    @DataProvider(name = "Paid5kUserDetails") 	
    public static Object[][] Paid5kUserDetails(ITestContext context) throws Exception {
        System.out.println("init Paid5kUserDetails");
		paid5kUserArray[0][0] = "mmoAutomated+5k080720105521@gmail.com";
		paid5kUserArray[0][1] = "mmoAutomated";
		paid5kUserArray[0][2] = "5k080720105521";
		return (paid5kUserArray);
    }
	
    @DataProvider(name = "ProfUserDetails") 	
    public static Object[][] ProfUserDetails(ITestContext context) throws Exception {
        System.out.println("init ProfUserDetails");
		profUserArray[0][0] = "mmoAutomated+Prof020720140740@gmail.com";
		profUserArray[0][1] = "mmoAutomated";
		profUserArray[0][2] = "Prof020720140740";
		return (profUserArray);
    }

	//@Test
	public void testSignUpFreeUSUserAndCompleteEmailRegistration() throws Exception {
		a.navigateToHomePage();
		freeUSUserArray =  a.signUpUser("free", "US", "N");
		Reporter.log("signUpFreeUSUserID: " + freeUSUserArray[0][0], true);
		Reporter.log("signUpFreeUSUserFirstName: " + freeUSUserArray[0][1], true);
		Reporter.log("signUpFreeUSUserSecondName: " + freeUSUserArray[0][2], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", freeUSUserArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(freeUSUserArray[0][0], freeUSUserArray[0][1], freeUSUserArray[0][2]  , claimTokenID);
	}
	
	//@Test
	public void testSignUpFreeNonUSUserAndCompleteEmailRegistration() throws Exception {
		a.navigateToHomePage();
		freeNonUSUserArray =  a.signUpUser("free", "NonUS", "N");
		Reporter.log("signUpFreeNonUSUserID: " + freeNonUSUserArray[0][0], true);
		Reporter.log("signUpFreeNonUSUserFirstName: " + freeNonUSUserArray[0][1], true);
		Reporter.log("signUpFreeNonUSUserSecondName: " + freeNonUSUserArray[0][2], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", freeNonUSUserArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(freeNonUSUserArray[0][0], freeNonUSUserArray[0][1], freeNonUSUserArray[0][2]  , claimTokenID);
	}
	
	@Test
	public void testSignUpPaid5kUserAndCompleteEmailRegistration() throws Exception {
		a.navigateToHomePage();
		paid5kUserArray =  a.signUpUser("5k", "US", "N");
		Reporter.log("signUpPaid5kUserID: " + paid5kUserArray[0][0], true);
		Reporter.log("signUpPaid5kUserFirstName: " + paid5kUserArray[0][1], true);
		Reporter.log("signUpPaid5kUserSecondName: " + paid5kUserArray[0][2], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", paid5kUserArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(paid5kUserArray[0][0], paid5kUserArray[0][1], paid5kUserArray[0][2]  , claimTokenID);
	}
	
	//@Test
	public void testSignUpPaidProfUserAndCompleteEmailRegistration() throws Exception {
		a.navigateToHomePage();
		profUserArray =  a.signUpUser("prof", "US", "N");
		Reporter.log("signUpPaidProfUserID: " + profUserArray[0][0], true);
		Reporter.log("signUpPaidProfUserFirstName: " + profUserArray[0][1], true);
		Reporter.log("signUpPaidProfUserSecondName: " + profUserArray[0][2], true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", profUserArray[0][0], EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(profUserArray[0][0], profUserArray[0][1], profUserArray[0][2]  , claimTokenID);
		a.navigateToHomePage();
	}

	@AfterClass(groups = {"prerequisite"})
	public void testLogout() throws Exception {
		//a.logOut();
	}
}