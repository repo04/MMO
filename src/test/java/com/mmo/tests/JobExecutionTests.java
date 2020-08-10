package com.mmo.tests;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;

public class JobExecutionTests extends BaseClass {

	static String[][] saOutFileNamesArray = new String[2][1];
	static String[][] admin1OutFileNamesArray = new String[1][1];
	static String[][] admin2OutFileNamesArray = new String[1][1];
	static String[][] user1OutFileNamesArray = new String[1][1];
	static String[][] user2OutFileNamesArray = new String[1][1];

	private Actions a = new Actions();
	int x = 0;
	
	@DataProvider(name = "SAJobs")
	public static Object[][] SAJobs(ITestContext context) throws Exception {
		Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "executeJobs", "SAJobs");
		return (retObjArr);
	}

	@DataProvider(name = "Admin1Jobs")
	public static Object[][] Admin1Jobs(ITestContext context) throws Exception {
		Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "executeJobs", "Admin1Jobs");
		return (retObjArr);
	}

	@DataProvider(name = "User1Jobs")
	public static Object[][] User1Jobs(ITestContext context) throws Exception {
		Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "executeJobs", "User1Jobs");
		return (retObjArr);
	}

	@DataProvider(name = "Admin2Jobs")
	public static Object[][] Admin2Jobs(ITestContext context) throws Exception {
		Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "executeJobs", "Admin2Jobs");
		return (retObjArr);
	}

	@DataProvider(name = "User2Jobs")
	public static Object[][] User2Jobs(ITestContext context) throws Exception {
		Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "executeJobs", "User2Jobs");
		return (retObjArr);
	}

	@DataProvider(name = "checkValidations")
	public static Object[][] createData2(ITestContext context) throws Exception {
		Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "checkValidations", "checkValidations");
		return (retObjArr);
	}

	@DataProvider(name = "SAIdAndInputJobDetails")
	public static Iterator<Object[]> SAIdAndInputJobDetails(ITestContext context) throws Exception {
		System.out.println("init SAIdAndInputJobDetails");
		return DataProviderUtility.cartesianProviderFrom(SignUp.FreeUSUserDetails(context), SAJobs(context));
	}

	@DataProvider(name = "Admin1IdAndInputJobDetails")
	public static Iterator<Object[]> Admin1IdAndInputJobDetails(ITestContext context) throws Exception {
		System.out.println("init Admin1IdAndInputJobDetails");
		return DataProviderUtility.cartesianProviderFrom(CreateSubAccountTests.SubscriptionAdminCreateAdminDetails(context), Admin1Jobs(context));
	}

	@DataProvider(name = "Admin2IdAndInputJobDetails")
	public static Iterator<Object[]> Admin2IdAndInputJobDetails(ITestContext context) throws Exception {
		System.out.println("init Admin2IdAndInputJobDetails");
		return DataProviderUtility.cartesianProviderFrom(CreateSubAccountTests.AdminCreateAdminDetails(context), Admin2Jobs(context));
	}

	@DataProvider(name = "User1IdAndInputJobDetails")
	public static Iterator<Object[]> User1IdAndInputJobDetails(ITestContext context) throws Exception {
		System.out.println("init User1IdAndInputJobDetails");
		return DataProviderUtility.cartesianProviderFrom(CreateSubAccountTests.SubscriptionAdminCreateUserDetails(context), User1Jobs(context));
	}

	@DataProvider(name = "User2IdAndInputJobDetails")
	public static Iterator<Object[]> User2IdAndInputJobDetails(ITestContext context) throws Exception {
		System.out.println("init User2IdAndInputJobDetails");
		return DataProviderUtility.cartesianProviderFrom(CreateSubAccountTests.AdminCreateUserDetails(context), User2Jobs(context));
	}

	@DataProvider(name = "SAOutFileNames")
	public static Object[][] SAOutFileNames(ITestContext context) throws Exception {
		System.out.println("init SAOutFileNamesList");
		return DataProviderUtility.appendOutFileNameToEachRow(SAJobs(context), saOutFileNamesArray);
	}

	@DataProvider(name = "Admin1OutFileNames")
	public static Object[][] Admin1OutFileNames(ITestContext context) throws Exception {
		System.out.println("init Admin1OutFileNames");
		return DataProviderUtility.appendOutFileNameToEachRow(Admin1Jobs(context), admin1OutFileNamesArray);
	}

	@DataProvider(name = "Admin2OutFileNames")
	public static Object[][] Admin2OutFileNames(ITestContext context) throws Exception {
		System.out.println("init Admin2OutFileNames");
		return DataProviderUtility.appendOutFileNameToEachRow(Admin2Jobs(context), admin2OutFileNamesArray);
	}

	@DataProvider(name = "User1OutFileNames")
	public static Object[][] User1OutFileNames(ITestContext context) throws Exception {
		System.out.println("init User1OutFileNames");
		return DataProviderUtility.appendOutFileNameToEachRow(User1Jobs(context), user1OutFileNamesArray);
	}

	@DataProvider(name = "User2OutFileNames")
	public static Object[][] User2OutFileNames(ITestContext context) throws Exception {
		System.out.println("init User2OutFileNames");
		return DataProviderUtility.appendOutFileNameToEachRow(User2Jobs(context), user2OutFileNamesArray);
	}

	@BeforeClass(groups = { "prerequisite" })
	public void testLogIn(ITestContext context) throws Exception {
		a.navigateToHomePage();
		a.navigateToLogin();
	}

	@Test(dataProvider = "SAIdAndInputJobDetails", groups = {"regressionSuite"})
	public void testSAUploadFileConfigureAndStartGeocoding(String userID, String firstName, String secondName, String inputFileName, String geocodingType, String autoDrag, String dragColumns,
														   String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
														   String matchMode) throws Exception {
//		a.login(userID);
//		a.navigateToUploadFile();
//		saOutFileNamesArray[x][0] = a.uploadFileConfigureAndStartJob(secondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
//				outputFields, outputFormat, coordSystem, country, matchMode);
//		System.out.println("SAOutFileNamesArray: " + saOutFileNamesArray[x][0] + "\n");
//		x++;
//		a.logOut();
		saOutFileNamesArray[0][0] = "UnevenInvertedCommas_FG_FreeUS100820135236";
		saOutFileNamesArray[1][0] = "JobSuccessNoAddressGeocode_FG_FreeUS100820135236";
	}

	@Test(dataProvider = "Admin1IdAndInputJobDetails", groups = {"regressionSuite"})
	public void testAdmin1UploadFileConfigureAndStartGeocoding(String userID, String firstName, String secondName,
															   String inputFileName, String geocodingType, String autoDrag, String dragColumns,
															   String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
															   String matchMode) throws Exception {
//        a.login(userID);
//        a.navigateToUploadFile();
//        admin1OutFileNamesArray[0][0] = a.uploadFileConfigureAndStartJob(secondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
//                outputFields, outputFormat, coordSystem, country, matchMode);
//        System.out.println("outFileName: " + admin1OutFileNamesArray[0][0] + "\n");
//        a.logOut();
		admin1OutFileNamesArray[0][0] = "UnevenInvrtdComma_RG_FreeUSSA_Admin100820135427";
	}

	@Test(dataProvider = "User1IdAndInputJobDetails", groups = {"regressionSuite"})
	public void testUser1UploadFileConfigureAndStartGeocoding(String userID, String firstName, String secondName,
															  String inputFileName, String geocodingType, String autoDrag, String dragColumns,
															  String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
															  String matchMode) throws Exception {
//        a.login(userID);
//        a.navigateToUploadFile();
//        user1OutFileNamesArray[0][0] = a.uploadFileConfigureAndStartJob(secondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
//                outputFields, outputFormat, coordSystem, country, matchMode);
//        System.out.println("outFileName: " + user1OutFileNamesArray[0][0] + "\n");
//        a.logOut();
		user1OutFileNamesArray[0][0] = "EmptyLinesAtEnd_RG_XY_FreeUSSA_User100820135526";
	}

	@Test(dataProvider = "Admin2IdAndInputJobDetails", groups = {"regressionSuite"})
	public void testAdmin2UploadFileConfigureAndStartGeocoding(String userID, String firstName, String secondName,
															   String inputFileName, String geocodingType, String autoDrag, String dragColumns,
															   String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
															   String matchMode) throws Exception {
//        a.login(userID);
//        a.navigateToUploadFile();
//        admin2OutFileNamesArray[0][0] = a.uploadFileConfigureAndStartJob(secondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
//                outputFields, outputFormat, coordSystem, country, matchMode);
//        System.out.println("outFileName: " + admin2OutFileNamesArray[0][0] + "\n");
//        a.logOut();
		admin2OutFileNamesArray[0][0] = "Geocoding_Temp_SHP_FreeUSSAAd_Admin100820135621";
	}

	@Test(dataProvider = "User2IdAndInputJobDetails", groups = {"regressionSuite"})
	public void testUser2UploadFileConfigureAndStartGeocoding(String userID, String firstName, String secondName,
															  String inputFileName, String geocodingType, String autoDrag, String dragColumns,
															  String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
															  String matchMode) throws Exception {
//        a.login(userID);
//        a.navigateToUploadFile();
//        user2OutFileNamesArray[0][0] = a.uploadFileConfigureAndStartJob(secondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
//                outputFields, outputFormat, coordSystem, country, matchMode);
//        System.out.println("outFileName: " + user2OutFileNamesArray[0][0] + "\n");
//        a.logOut();
		user2OutFileNamesArray[0][0] = "RevIn27700_Ext_FreeUSSAAd_User100820135718";
	}

	//@Test(dataProvider = "checkValidations", groups = { "regressionSuite" })
	public void testUploadIncorrectFilesAndCheckValidations(String inputFileName, String geocodingType, String expectedMessage) throws Exception {
		a.navigateToDashboard();
		a.navigateToUploadFile();
		a.uploadIncorrectFilesAndCheckValidations(inputFileName, geocodingType, expectedMessage);
	}

	//@Test()
	public void testJobDetails() throws Exception {
		a.navigateToDashboard();
		//a.viewJobDetails(saOutFileNamesArray[0][0]);
	}
}
