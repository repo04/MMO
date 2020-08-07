package com.mmo.tests;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;

public class Job extends BaseClass {

	static String[][] SAOutFileNamesArray = new String[2][1];
	private Actions a = new Actions();
	int x = 0;
	
	@DataProvider(name = "SAJobs")
	public static Object[][] SAJobs(ITestContext context) throws Exception {
		Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "executeJobs", "SAJobs");
		return (retObjArr);
	}

	@DataProvider(name = "checkValidations")
	public static Object[][] createData2(ITestContext context) throws Exception {
		Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "checkValidations", "checkValidations");
		return (retObjArr);
	}

	@DataProvider(name = "SAOutFileNames")
	public static Object[][] SAOutFileNames(ITestContext context) throws Exception {
        System.out.println("init SAOutFileNamesList");
        return DataProviderUtility.appendOutFileNameToEachRow(SAJobs(context), SAOutFileNamesArray);
    }

	/*@BeforeClass(groups = { "prerequisite" })
	public void testLogIn(ITestContext context) throws Exception {
		a.navigateToLogin();
		a.login("mmoAutomated+geoTax290720162455@gmail.com");
	}

	@AfterClass(groups = { "prerequisite" })
	public void testLogOut(ITestContext context) throws Exception {
		a.logOut();
	}*/

	@Test(dataProvider = "SAJobs", groups = {"regressionSuite"})
	public void testSAUploadFileConfigureAndStartGeocoding(String inputFileName, String geocodingType, String autoDrag, String dragColumns,
			String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
			String matchMode) throws Exception {
//		a.navigateToDashboard();
//		a.navigateToUploadFile();
//		SAOutFileNamesArray[x][0] = a.uploadFileConfigureAndStartJob(inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
//				outputFields, outputFormat, coordSystem, country, matchMode);
		//SAOutFileNamesArray[x][0] = inputFileName.substring(0, inputFileName.lastIndexOf(".")) + "_" + u.currentDateTime();
		SAOutFileNamesArray[0][0] = "UnevenInvertedCommas_FG_070820143527";
		SAOutFileNamesArray[1][0] = "JobSuccessNoAddressGeocode_FG_070820143548";
//		Thread.sleep(10000);
//		System.out.println("SAOutFileNamesArray: " + SAOutFileNamesArray[x][0] + "\n");
//		x++;
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
		a.viewJobDetails(SAOutFileNamesArray[0][0]);
	}
}
