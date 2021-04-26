package com.mmo.tests;

import com.mmo.util.*;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Reporter;

public class SignUpTests extends BaseClass{

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
//		freeUSUserArray[0][0] = "mmoautomated+FreeUS080421134249@gmail.com";
//		freeUSUserArray[0][1] = "mmoAutomated";
//		freeUSUserArray[0][2] = "FreeUS080421134249";
		return freeUSUserArray;
    }
	
    @DataProvider(name = "FreeNonUSUserDetails") 	
    public static Object[][] FreeNonUSUserDetails(ITestContext context) throws Exception {
        System.out.println("init FreeNonUSUserDetails");
//		freeNonUSUserArray[0][0] = "mmoAutomated+FreeUS080421115109@gmail.com";
//		freeNonUSUserArray[0][1] = "mmoAutomated";
//		freeNonUSUserArray[0][2] = "FreeUS080421115109";
		return freeNonUSUserArray;
    }
	
    @DataProvider(name = "Paid5kUserDetails") 	
    public static Object[][] Paid5kUserDetails(ITestContext context) throws Exception {
        System.out.println("init Paid5kUserDetails");
//		paid5kUserArray[0][0] = "mmoAutomated+5k080421134407@gmail.com";
//		paid5kUserArray[0][1] = "mmoAutomated";
//		paid5kUserArray[0][2] = "5k080421134407";
		return paid5kUserArray;
    }
	
    @DataProvider(name = "ProfUserDetails") 	
    public static Object[][] ProfUserDetails(ITestContext context) throws Exception {
        System.out.println("init ProfUserDetails");
//		profUserArray[0][0] = "mmoAutomated+freeus270121155055@gmail.com";
//		profUserArray[0][1] = "mmoAutomated";
//		profUserArray[0][2] = "FreeUS270121155055";
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
//		return DataProviderUtility.append2DArrayVertically(FreeNonUSUserDetails(context), FreeUSUserDetails(context));
		return DataProviderUtility.append2DArrayVertically(FreeUSUserDetails(context));
	}

	@DataProvider(name = "AllPaidSubscriptionAdminDetails")
	public static Object[][] AllPaidSubscriptionAdminDetails(ITestContext context) throws Exception {
		System.out.println("init AllPaidSubscriptionAdminDetails");
		//return DataProviderUtility.append2DArrayVertically(Paid5kUserDetails(context), ProfUserDetails(context));
		return DataProviderUtility.append2DArrayVertically(Paid5kUserDetails(context));
	}

	@BeforeClass(groups = {"prerequisite"})
	public void testVerifyHomePage() throws Exception {
		a.navigateToHomePage();
		a.verifyHomePage();
		a.verifyFooters();
	}

	@Test(groups = {"regressionSuite"})
	public void testSignUpFreeUSUserAndCompleteEmailRegistration() throws Exception {
		emailUtils.deleteAllEmails(EmailUtils.EmailFolder.STARTUSINGMMO);
		a.navigateToHomePage();
		freeUSUser =  a.signUpUser("free", "US", "N");
		freeUSUserArray[0][0] = freeUSUser;
		freeUSUserArray[0][1] = u.getFirstName(freeUSUser);
		freeUSUserArray[0][2] = u.getSecondName(freeUSUser);
		Reporter.log("signUpFreeUSUserID: " + freeUSUser + " created <br/>", true);
		emailUtils.waitForEmailReceived("You're ready to start using MapMarker", EmailUtils.EmailFolder.STARTUSINGMMO, 1);
		Reporter.log("Free US User Email Received" + "<br/>", true);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", freeUSUser, EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(freeUSUser, u.getFirstName(freeUSUser), u.getSecondName(freeUSUser), claimTokenID);
		Reporter.log("Free US user registered" + "<br/>", true);
	}

//	@Test(groups = {"regressionSuite"})
	public void testSignUpFreeNonUSUserAndCompleteEmailRegistration() throws Exception {
		emailUtils.deleteAllEmails(EmailUtils.EmailFolder.STARTUSINGMMO);
		a.navigateToHomePage();
		freeNonUSUser =  a.signUpUser("free", "NonUS", "N");
		freeNonUSUserArray[0][0] = freeNonUSUser;
		freeNonUSUserArray[0][1] = u.getFirstName(freeNonUSUser);
		freeNonUSUserArray[0][2] = u.getSecondName(freeNonUSUser);
		Reporter.log("signUpFreeNonUSUserID: " + freeNonUSUser + " created <br/>", true);
		emailUtils.waitForEmailReceived("You're ready to start using MapMarker", EmailUtils.EmailFolder.STARTUSINGMMO, 1);
		Reporter.log("Non Free US User Email Received" + "<br/>", true);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", freeNonUSUser, EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(freeNonUSUser, u.getFirstName(freeNonUSUser), u.getSecondName(freeNonUSUser), claimTokenID);
		Reporter.log("Non Free US user registered" + "<br/>", true);
	}

	@Test(groups = {"regressionSuite"})
	public void testSignUpPaid5kUserAndCompleteEmailRegistration() throws Exception {
		emailUtils.deleteAllEmails(EmailUtils.EmailFolder.STARTUSINGMMO);
		a.navigateToHomePage();
		paid5kUser =  a.signUpUser("5k", "US", "N");
		paid5kUserArray[0][0] = paid5kUser;
		paid5kUserArray[0][1] = u.getFirstName(paid5kUser);
		paid5kUserArray[0][2] = u.getSecondName(paid5kUser);
		Reporter.log("signUpPaid5kUserID: " + paid5kUser + " created <br/>", true);
		emailUtils.waitForEmailReceived("You're ready to start using MapMarker", EmailUtils.EmailFolder.STARTUSINGMMO, 1);
		Reporter.log("Paid 5k US User Email Received" + "<br/>", true);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", paid5kUser, EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(paid5kUser, u.getFirstName(paid5kUser), u.getSecondName(paid5kUser), claimTokenID);
		Reporter.log("Paid US user registered" + "<br/>", true);
	}

	//@Test(groups = {"regressionSuite"})
	public void testSignUpPaidProfUserAndCompleteEmailRegistration() throws Exception {
		emailUtils.deleteAllEmails(EmailUtils.EmailFolder.STARTUSINGMMO);
		a.navigateToHomePage();
		profUser =  a.signUpUser("prof", "US", "N");
		profUserArray[0][0] = profUser;
		profUserArray[0][1] = u.getFirstName(profUser);
		profUserArray[0][2] = u.getSecondName(profUser);
		Reporter.log("signUpPaidProfUserID: " + profUser + " created <br/>", true);
		emailUtils.waitForEmailReceived("You're ready to start using MapMarker", EmailUtils.EmailFolder.STARTUSINGMMO, 1);
		Reporter.log("Prof US User Email Received" + "<br/>", true);
		String claimTokenID = emailUtils.getToken("You're ready to start using MapMarker", profUser, EmailUtils.EmailFolder.STARTUSINGMMO);
		a.completeRegistration(profUser, u.getFirstName(profUser), u.getSecondName(profUser), claimTokenID);
		Reporter.log("Prof US user registered" + "<br/>", true);
	}
}