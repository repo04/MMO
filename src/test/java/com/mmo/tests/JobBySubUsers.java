package com.mmo.tests;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;
import com.mmo.util.DataProviderUtility;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;

public class JobBySubUsers extends BaseClass {
    static String[][] admin1OutFileNamesArray = new String[1][1];
    static String[][] admin2OutFileNamesArray = new String[1][1];
    static String[][] user1OutFileNamesArray = new String[1][1];
    static String[][] user2OutFileNamesArray = new String[1][1];

    private Actions a = new Actions();

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

    /*@BeforeClass(groups = { "prerequisite" })
    public void testLogIn(ITestContext context) throws Exception {
        a.navigateToHomePage();
        a.navigateToLogin();
    }

    @AfterMethod(groups = { "prerequisite" })
    public void testLogOut(ITestContext context) throws Exception {
        a.logOut();
    }*/

    @Test(dataProvider = "Admin1Jobs", groups = {"regressionSuite"})
    public void testAdmin1UploadFileConfigureAndStartGeocoding(String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                               String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                               String matchMode) throws Exception {
        /*a.login("mmoAutomated+290720162455Admin1@gmail.com");
        a.navigateToDashboard();
        a.navigateToUploadFile();
        admin1OutFileNamesArray[0][0] = a.uploadFileConfigureAndStartJob(inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
                outputFields, outputFormat, coordSystem, country, matchMode);
        admin1OutFileNamesArray[0][0] = inputFileName.substring(0, inputFileName.lastIndexOf(".")) + "_" + u.currentDateTime();*/
        admin1OutFileNamesArray[0][0] = "UnevenInvertedCommas_RG_070820143645";
        Thread.sleep(10000);
        System.out.println("outFileName: " + admin1OutFileNamesArray[0][0] + "\n");
    }

    @Test(dataProvider = "User1Jobs", groups = {"regressionSuite"})
    public void testUser1UploadFileConfigureAndStartGeocoding(String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                              String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                              String matchMode) throws Exception {
        /*a.login("mmoAutomated+290720162455User1@gmail.com");
        a.navigateToDashboard();
        a.navigateToUploadFile();
        user1OutFileNamesArray[0][0] = a.uploadFileConfigureAndStartJob(inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
                outputFields, outputFormat, coordSystem, country, matchMode);
        user1OutFileNamesArray[0][0] = inputFileName.substring(0, inputFileName.lastIndexOf(".")) + "_" + u.currentDateTime();*/
        user1OutFileNamesArray[0][0] = "MultipleEmptyLinesAtEnd_RG_XY_070820143810";
        Thread.sleep(10000);
        System.out.println("outFileName: " + user1OutFileNamesArray[0][0] + "\n");
    }

    @Test(dataProvider = "Admin2Jobs", groups = {"regressionSuite"})
    public void testAdmin2UploadFileConfigureAndStartGeocoding(String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                               String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                               String matchMode) throws Exception {
        /*a.login("mmoAutomated+290720162455Admin2@gmail.com");
        a.navigateToDashboard();
        a.navigateToUploadFile();
        admin2OutFileNamesArray[0][0] = a.uploadFileConfigureAndStartJob(inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
                outputFields, outputFormat, coordSystem, country, matchMode);
        admin2OutFileNamesArray[0][0] = inputFileName.substring(0, inputFileName.lastIndexOf(".")) + "_" + u.currentDateTime();*/
        admin2OutFileNamesArray[0][0] = "MapMarker_Geocoding_Template_SHP_070820143726";
        Thread.sleep(10000);
        System.out.println("outFileName: " + admin2OutFileNamesArray[0][0] + "\n");
    }

    @Test(dataProvider = "User2Jobs", groups = {"regressionSuite"})
    public void testUser2UploadFileConfigureAndStartGeocoding(String inputFileName, String geocodingType, String autoDrag, String dragColumns,
                                                              String dropFieldsToGeocode, String outputFields, String outputFormat, String coordSystem, String country,
                                                              String matchMode) throws Exception {
        /*a.login("mmoAutomated+290720162455User2@gmail.com");
        a.navigateToDashboard();
        a.navigateToUploadFile();
        user2OutFileNamesArray[0][0] = a.uploadFileConfigureAndStartJob(inputFileName, geocodingType, autoDrag, dragColumns, dropFieldsToGeocode,
                outputFields, outputFormat, coordSystem, country, matchMode);
        user2OutFileNamesArray[0][0] = inputFileName.substring(0, inputFileName.lastIndexOf(".")) + "_" + u.currentDateTime();*/
        user2OutFileNamesArray[0][0] = "MapMarker_ReverseGeocoding_Template_TAB_070820143856";
        Thread.sleep(10000);
        System.out.println("outFileName: " + user2OutFileNamesArray[0][0] + "\n");
    }
}
