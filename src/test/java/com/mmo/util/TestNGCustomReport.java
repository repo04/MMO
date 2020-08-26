package com.mmo.util;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.Count;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestNGCustomReport extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        ScreenShot(tr);
    }

    @Override
    public void onStart(ITestContext testContext) {
        System.out.print("Class: " + testContext.getName() + "\n");
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        //Do Nothing
    }

    @Override
    public void onConfigurationFailure(ITestResult tr) {
        ScreenShot(tr);
    }
    
    //Capture screenshot on TestFailure
    public void ScreenShot(ITestResult result) {

        try {

            String NewFileNamePath;
            String methodName = result.getName();
            System.out.println("***" + methodName);
            System.out.println("***" + result.isSuccess());
            
            //Get current date time with Date() to create unique file name
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMMyy__hhmmaa");
            // get current date time with Date()
            Date date = new Date();

            if (!(new File(BaseClass.directory.getCanonicalPath() + File.separator + "target" + File.separator + "surefire-reports" + File.separator + "screenshots")).exists()) {
                new File(BaseClass.directory.getCanonicalPath() + File.separator + "target" + File.separator + "surefire-reports" + File.separator + "screenshots").mkdir();
            }

            NewFileNamePath = BaseClass.directory.getCanonicalPath() + File.separator + "target" + File.separator + "surefire-reports" + File.separator + "screenshots"
                    + File.separator + methodName + "_" + dateFormat.format(date) + ".png";

            System.out.println(NewFileNamePath);
            
            File screenshot = ((TakesScreenshot) BaseClass.driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(NewFileNamePath));
            
//            Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(BaseClass.driver);
//            ImageIO.write(screenshot.getImage(),"jpg",new File(NewFileNamePath));
            
            Reporter.log(methodName + " failed; Click on image to enlarge<br/>"
                    + "<a target=\"_blank\" href=\"" + "file:///" + NewFileNamePath + "\"><img src=\"file:///" + NewFileNamePath
                    + "\" alt=\"\"" + "height='100' width='100'/></a><br/>");
            Reporter.setCurrentTestResult(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}