package com.mmo.tests;

import org.testng.ITestContext; 
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass; 


public class Job extends BaseClass{

	Actions a = new Actions();

	@DataProvider(name = "DP1")
	public Object[][] createData1() throws Exception{
		Object[][] retObjArr=u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\data\\expectedFiles\\NEW.xls","jobs",
				"jobDetails");
		return(retObjArr);	
	}

	@BeforeClass(groups = {"prerequisite"})
	public void testLogIn(ITestContext context) throws Exception {
		a.navigateToLogin();
		a.login("autoMMOPb+FreeUS110419124614@gmail.com");

	}

	@Test(dataProvider = "DP1")
	public void testUploadFileConfigureAndStartGeocoding(String inputFileName, String dragColumns, String dropFieldsToGeocode, String outputFields,
			String outputFormat, String country, String matchMode) throws Exception {

		a.navigateToDashboard();
		a.navigateToUploadFile();
		a.uploadFileConfigureAndStartJob(inputFileName, dragColumns, dropFieldsToGeocode, outputFields,
				outputFormat, country, matchMode);
		//a.getJobDetailsAfterCompletion("Cntrs204_180419161121");
	}

	//@Test()
	public void testJobStatus() throws Exception {
		a.navigateToDashboard();
	}
}
