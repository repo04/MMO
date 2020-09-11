package com.mmo.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestNGCustomReport2 extends TestListenerAdapter {

    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;

    public static String getScreenshot(String screenshotName) throws Exception {
        //below line is just to append the date format with the screenshot name to avoid duplicate names
        String dateName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) BaseClass.driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + File.separator +"FailedTestsScreenshots" + File.separator + screenshotName + "_" + dateName+".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        //Returns the captured file path
        System.out.println("PATH: " + destination);
        return destination;
    }

    @Override
    public void onTestFailure(ITestResult tr){
        logger = extent.createTest(tr.getName()); // create new entry in th report
        logger.log(Status.FAIL,MarkupHelper.createLabel(tr.getName(),ExtentColor.RED)); // send the passed information to the report with GREEN color highlighted

        //String screenshotPath=System.getProperty("user.dir")+"\\Screenshots\\"+tr.getName()+".png";
        try {
            logger.fail("Screenshot is below:" + logger.addScreenCaptureFromPath(getScreenshot(tr.getName())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(ITestContext testContext) {
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+ "/test-output/myReport.html"); //specify location of the report
        htmlReporter.loadXMLConfig("D:\\MMOnline\\Automation\\workspace\\tests\\src\\test\\resources\\extent-config.xml");

        extent=new ExtentReports();

        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host name","localhost");
        extent.setSystemInfo("Environemnt","QA");
        extent.setSystemInfo("user","somesh");

        htmlReporter.config().setDocumentTitle("Automation Report"); // Tile of report
        htmlReporter.config().setReportName("Functional Testing"); // name of the report
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); //location of the chart
        htmlReporter.config().setTheme(Theme.STANDARD);
    }

    @Override
    public void onTestSuccess(ITestResult tr)
    {
        logger = extent.createTest(tr.getName()); // create new entry in th report
        logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN)); // send the passed information to the report with GREEN color highlighted
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        //Do Nothing
    }

    @Override
    public void onConfigurationFailure(ITestResult tr) {
        //ScreenShot(tr);
    }

    @Override
    public void onTestSkipped(ITestResult tr)
    {
        logger = extent.createTest(tr.getName()); // create new entry in th report
        logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
    }

    @Override
    public void onFinish(ITestContext testContext)
    {
        extent.flush();
    }
}
