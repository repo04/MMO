package com.mmo.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class Transform implements IAnnotationTransformer {
	
	String DependentMethods[] = null;

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// TODO Auto-generated method stub
		
		if ("testWaitForJobToGetCompleteDownloadAndCompare".equals(testMethod.getName())) {
            System.out.println("Inside testWaitForJobToGetCompleteDownloadAndCompare");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.Job.testUploadFileConfigureAndStartGeocoding";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testJobDetails".equals(testMethod.getName())) {
            System.out.println("Inside testJobDetails");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.Job.testWaitForJobToGetCompleteDownloadAndCompare";
            annotation.setDependsOnMethods(DependentMethods);
        }
		
	}

}
