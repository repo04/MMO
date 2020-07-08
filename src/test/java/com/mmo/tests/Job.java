package com.mmo.tests;

import java.util.ArrayList;
import java.util.Iterator;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;

public class Job extends BaseClass {

	String[][] outFileNamesArray = new String[2][1];
	private Actions a = new Actions();
//	ArrayList<String> outFileNamesList = new ArrayList<String>();
//	ArrayList<String> outFileFormatList = new ArrayList<String>();
	int x = 0;
	
	@DataProvider(name = "defaultJobs")
	public Object[][] createData1(ITestContext context) throws Exception {
		Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "defaultJobs", "defaultJobs");
		return (retObjArr);
	}
	
	@DataProvider(name = "checkValidations")
	public Object[][] createData2(ITestContext context) throws Exception {
		Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "checkValidations", "checkValidations");
		return (retObjArr);
	}

	@DataProvider(name = "OutFileNames")
	public Object[][] OutFileNames(ITestContext context) throws Exception {
		System.out.println("init OutFileNames");
		return (outFileNamesArray);
	}
	
	@DataProvider(name = "defaultJobDetails")
	public Object[][] defaultJobDetails(ITestContext context) throws Exception {
        System.out.println("init defaultJobDetails");
        return DataProviderUtility.cartesianProviderFrom2(createData1(context), OutFileNames(context));
    }

	@BeforeClass(groups = { "prerequisite" })
	public void testLogIn(ITestContext context) throws Exception {
		a.navigateToLogin();
		a.login("mmoAutomated+5k080720104238@gmail.com");
	}

	//@Test(dataProvider = "defaultJobs")
	public void testUploadFileConfigureAndStartGeocoding(String inputFileName, String geocodingType, String autoDrag, String dragColumns,
			String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
			String matchMode) throws Exception {
//		a.navigateToDashboard();
//		a.navigateToUploadFile();
//		outFileNamesArray[x][0] = a.uploadFileConfigureAndStartJob(inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
//				outputFields, outputFormat, coordSystem, country, matchMode);
		outFileNamesArray[x][0] = u.currentDateTime();
		System.out.println("Time: " + outFileNamesArray[x][0] + "\n");
		Thread.sleep(1000);
		x++;
//		return new Object[][]{{inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
//			outputFields, outputFormat, coordSystem, country, matchMode, outFileNamesArray[0][0]}};
//		outFileFormatList.add(outputFormat);
	}
	
	//@Test(dataProvider = "defaultJobDetails")
	public void testWaitForJobToGetCompleteDownloadAndCompare(String inputFileName, String geocodingType, String autoDrag, String dragColumns,
			String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
			String matchMode, String outFileName) throws Exception {
		//a.navigateToDashboard();
		System.out.println("File size: " + inputFileName + "\n");
		System.out.println("File size: " + geocodingType + "\n");
		System.out.println("File size: " + autoDrag + "\n");
		System.out.println("File size: " + dragColumns + "\n");
		System.out.println("File size: " + dropFieldsToGeocode + "\n");
		System.out.println("File size: " + outputFields + "\n");
		System.out.println("File size: " + outputFormat + "\n");
		System.out.println("File size: " + coordSystem + "\n");
		System.out.println("File size: " + country + "\n");
		System.out.println("File size: " + matchMode + "\n");
		System.out.println("File size: " + outFileName + "\n");
//		for (int i = 0; i < outFileNamesList.size(); i++) {
//			a.waitforJobToGetComplete(outFileNamesList.get(i));
//			System.out.print("****Download File Start****:" + "\n");
//			a.downloadOutputFileAndCompare(outFileNamesList.get(i), outFileFormatList.get(i));
//		}
//		a.waitforJobToGetComplete(outFileName);
//		System.out.print("****Download File Start****:" + "\n");
//		a.downloadOutputFileAndCompare(outFileName, outputFormat);
	}

	@Test(dataProvider = "checkValidations")
	public void testUploadIncorrectFilesAndCheckValidations(String inputFileName, String geocodingType, String expectedMessage) throws Exception {
		a.navigateToDashboard();
		a.navigateToUploadFile();
		a.uploadIncorrectFilesAndCheckValidations(inputFileName, geocodingType, expectedMessage);
	}

	//@Test()
	public void testJobDetails() throws Exception {
		a.navigateToDashboard();
		a.viewJobDetails(outFileNamesArray[0][0]);		
	}
}
