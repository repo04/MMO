package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.util.Iterator;

public class JobsBySATests extends BaseClass {
    private Actions a = new Actions();
    static String[][] saOutFileNamesArray = new String[2][1];
    int x = 0;

    @DataProvider(name = "SAJobs")
    public static Object[][] SAJobs(ITestContext context) throws Exception {
        Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "executeJobs", "SAJobs");
        return (retObjArr);
    }

    @DataProvider(name = "SAIdAndInputJobDetails")
    public static Iterator<Object[]> SAIdAndInputJobDetails(ITestContext context) throws Exception {
        System.out.println("init SAIdAndInputJobDetails");
        return DataProviderUtility.cartesianProviderFrom(SignUp.FreeUSUserDetails(context), SAJobs(context));
    }

    @DataProvider(name = "SAOutFileNames")
    public static Object[][] SAOutFileNames(ITestContext context) throws Exception {
        System.out.println("init SAOutFileNames");
        //saOutFileNamesArray[0][0] = "mmoAutomated+FreeUS100820135236@gmail.com";
        return saOutFileNamesArray;
    }

    @DataProvider(name = "checkValidations")
    public static Object[][] checkValidations(ITestContext context) throws Exception {
        Object[][] retObjArr = u.getTableArray("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\NEW.xls", "checkValidations", "checkValidations");
        return (retObjArr);
    }

    @DataProvider(name = "SAJobWithOutFileNames")
    public static Object[][] SAJobWithOutFileNames(ITestContext context) throws Exception {
        System.out.println("init SAJobWithOutFileNames");
        return DataProviderUtility.appendOutFileNameToEachRow(SAJobs(context), SAOutFileNames(context));
    }

    @BeforeClass(groups = {"prerequisite"})
    public void testJobsBySANavigate() throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUp.class, groups = {"prerequisite"}, priority = 1)
    public void testJobsBySALogin(String userID, String userFirstName, String userSecondName) throws Exception {
        a.login(userID);
    }

    @Test(dataProvider = "SAIdAndInputJobDetails", groups = {"regressionSuite"}, priority = 2)
    public void testSAUploadFileConfigureAndStartGeocoding(String userID, String firstName, String secondName, String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                           String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                           String matchMode) throws Exception {
        a.navigateToUploadFile();
        saOutFileNamesArray[x][0] = a.uploadFileConfigureAndStartJob(secondName, inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
                outputFields, outputFormat, coordSystem, country, matchMode);
        System.out.println("SAOutFileNamesArray: " + saOutFileNamesArray[x][0] + "\n");
        x++;
//		saOutFileNamesArray[0][0] = "UnevenInvrtdComa_FG_FreeUS100820135236179";
//		saOutFileNamesArray[1][0] = "JobSuccessNoGeocode_FG_FreeUS100820135236928";
    }

    @Test(dataProvider = "checkValidations", groups = {"regressionSuite"}, priority = 3)
    public void testUploadIncorrectFilesAndCheckValidations(String inputFileName, String geocodingType, String expectedMessage) throws Exception {
        a.navigateToDashboard();
        a.navigateToUploadFile();
        a.uploadIncorrectFilesAndCheckValidations(inputFileName, geocodingType, expectedMessage);
    }

    @AfterClass(groups = {"prerequisite"})
    public void testLogout() throws Exception {
        a.logOut();
    }
}
