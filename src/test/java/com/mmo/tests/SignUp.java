package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Reporter;
import com.mmo.util.EmailUtils;

public class SignUp extends BaseClass{

	static String freeUSUser;
	static String freeNonUSUser;
	static String paid5kUser;
	static String profUser;
	static String[][] freeUSUserArray = new String[1][3];
	static String[][] freeNonUSUserArray = new String[1][3];
	static String[][] paid5kUserArray = new String[1][3];
	static String[][] profUserArray = new String[1][3];
	private Actions a = new Actions();

	@DataProvider(name = "FreeUSUserDetails")
    public static Object[][] FreeUSUserDetails(ITestContext context) throws Exception {
        System.out.println("init FreeUSUserDetails");
		freeUSUserArray[0][0] = "mmoAutomated+FreeUS290720152158@gmail.com";
		freeUSUserArray[0][1] = "mmoAutomated";
		freeUSUserArray[0][2] = "FreeUS290720152158";
		return freeUSUserArray;
    }
	
    @DataProvider(name = "FreeNonUSUserDetails") 	
    public static Object[][] FreeNonUSUserDetails(ITestContext context) throws Exception {
        System.out.println("init FreeNonUSUserDetails");
//		freeNonUSUserArray[0][0] = "mmoAutomated+FreeNonUS280720175857@gmail.com";
//		freeNonUSUserArray[0][1] = "mmoAutomated";
//		freeNonUSUserArray[0][2] = "FreeNonUS280720175857";
		return freeNonUSUserArray;
    }
	
    @DataProvider(name = "Paid5kUserDetails") 	
    public static Object[][] Paid5kUserDetails(ITestContext context) throws Exception {
        System.out.println("init Paid5kUserDetails");
//		paid5kUserArray[0][0] = "mmoAutomated+5k280720180105@gmail.com";
//		paid5kUserArray[0][1] = "mmoAutomated";
//		paid5kUserArray[0][2] = "5k280720180105";
		return paid5kUserArray;
    }
	
    @DataProvider(name = "ProfUserDetails") 	
    public static Object[][] ProfUserDetails(ITestContext context) throws Exception {
        System.out.println("init ProfUserDetails");
//		profUserArray[0][0] = "mmoAutomated+Prof280720180221@gmail.com";
//		profUserArray[0][1] = "mmoAutomated";
//		profUserArray[0][2] = "Prof280720180221";
		return profUserArray;
    }

	@DataProvider(name = "AllSubscriptionAdminDetails")
	public static Object[][] AllSubscriptionAdminDetails(ITestContext context) throws Exception {
		System.out.println("init AllSubscriptionAdminDetails");
		return DataProviderUtility.append2DArrayVertically(AllFreeSubscriptionAdminDetails(context), AllPaidSubscriptionAdminDetails(context));
	}

	@DataProvider(name = "AllFreeSubscriptionAdminDetails")
	public static Object[][] AllFreeSubscriptionAdminDetails(ITestContext context) throws Exception {
		System.out.println("init AllFreeSubscriptionAdminDetails");
		return DataProviderUtility.append2DArrayVertically(FreeNonUSUserDetails(context), FreeUSUserDetails(context));
	}

	@DataProvider(name = "AllPaidSubscriptionAdminDetails")
	public static Object[][] AllPaidSubscriptionAdminDetails(ITestContext context) throws Exception {
		System.out.println("init AllPaidSubscriptionAdminDetails");
		return DataProviderUtility.append2DArrayVertically(Paid5kUserDetails(context), ProfUserDetails(context));
	}

	@BeforeClass(groups = {"prerequisite"})
	public void testVerifyHomePage() throws Exception {
		a.navigateToHomePage();
		a.verifyHomePage();
		a.verifyFooters();
	}

	@Test(groups = {"regressionSuite", "sanitySuite"})
	public void testSignUpFreeUSUserAndCompleteEmailRegistration() throws Exception {
		emailUtils.markAllEmailsAsUnread(EmailUtils.EmailFolder.STARTUSINGMMO);
		a.navigateToHomePage();
		freeUSUser =  a.signUpUser("free", "US", "N");
		freeUSUserArray[0][0] = freeUSUser;
		freeUSUserArray[0][1] = u.getFirstName(freeUSUser);
		freeUSUserArray[0][2] = u.getSecondName(freeUSUser);
		Reporter.log("signUpFreeUSUserID: " + freeUSUser, true);
		Reporter.log("signUpFreeUSUserFirstName: " + u.getFirstName(freeUSUser), true);
		Reporter.log("signUpFreeUSUserSecondName: " + u.getSecondName(freeUSUser), true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", freeUSUser, EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(freeUSUser, u.getFirstName(freeUSUser), u.getSecondName(freeUSUser), claimTokenID);
	}

	@Test(groups = {"regressionSuite"})
	public void testSignUpFreeNonUSUserAndCompleteEmailRegistration() throws Exception {
		emailUtils.markAllEmailsAsUnread(EmailUtils.EmailFolder.STARTUSINGMMO);
		a.navigateToHomePage();
		freeNonUSUser =  a.signUpUser("free", "NonUS", "N");
		freeNonUSUserArray[0][0] = freeNonUSUser;
		freeNonUSUserArray[0][1] = u.getFirstName(freeNonUSUser);
		freeNonUSUserArray[0][2] = u.getSecondName(freeNonUSUser);
		Reporter.log("signUpFreeNonUSUserID: " + freeNonUSUser, true);
		Reporter.log("signUpFreeNonUSUserFirstName: " + u.getFirstName(freeNonUSUser), true);
		Reporter.log("signUpFreeNonUSUserSecondName: " + u.getSecondName(freeNonUSUser), true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", freeNonUSUser, EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(freeNonUSUser, u.getFirstName(freeNonUSUser), u.getSecondName(freeNonUSUser), claimTokenID);
	}

	@Test(groups = {"regressionSuite"})
	public void testSignUpPaid5kUserAndCompleteEmailRegistration() throws Exception {
		emailUtils.markAllEmailsAsUnread(EmailUtils.EmailFolder.STARTUSINGMMO);
		a.navigateToHomePage();
		paid5kUser =  a.signUpUser("5k", "US", "N");
		paid5kUserArray[0][0] = paid5kUser;
		paid5kUserArray[0][1] = u.getFirstName(paid5kUser);
		paid5kUserArray[0][2] = u.getSecondName(paid5kUser);
		Reporter.log("signUpPaid5kUserID: " + paid5kUser, true);
		Reporter.log("signUpPaid5kUserFirstName: " + u.getFirstName(paid5kUser), true);
		Reporter.log("signUpPaid5kUserSecondName: " + u.getSecondName(paid5kUser), true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", paid5kUser, EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(paid5kUser, u.getFirstName(paid5kUser), u.getSecondName(paid5kUser), claimTokenID);
	}

	@Test(groups = {"regressionSuite"})
	public void testSignUpPaidProfUserAndCompleteEmailRegistration() throws Exception {
		emailUtils.markAllEmailsAsUnread(EmailUtils.EmailFolder.STARTUSINGMMO);
		a.navigateToHomePage();
		profUser =  a.signUpUser("prof", "US", "N");
		profUserArray[0][0] = profUser;
		profUserArray[0][1] = u.getFirstName(profUser);
		profUserArray[0][2] = u.getSecondName(profUser);
		Reporter.log("signUpPaidProfUserID: " + profUser, true);
		Reporter.log("signUpPaidProfUserFirstName: " + u.getFirstName(profUser), true);
		Reporter.log("signUpPaidProfUserSecondName: " + u.getSecondName(profUser), true);
		Thread.sleep(10000);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", profUser, EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(profUser, u.getFirstName(profUser), u.getSecondName(profUser), claimTokenID);
	}
}