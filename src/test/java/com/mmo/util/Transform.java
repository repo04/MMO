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

//        if ("testUserUpdateAndVerifyCardDetails".equals(testMethod.getName())) {
//            System.out.println("Inside testUserUpdateAndVerifyCardDetails");
//            DependentMethods = new String[1];
//            DependentMethods[0] = "com.mmo.tests.UserDetailTests.testUserVerifyDetails";
//            annotation.setDependsOnMethods(DependentMethods);
//        }

        if ("testAdminCreateAdmin".equals(testMethod.getName())) {
            System.out.println("Inside testAdminCreateAdmin");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.CreateSubAccountTests.testSubscriptionAdminCreateAdmin";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testAdminCreateUser".equals(testMethod.getName())) {
            System.out.println("Inside testAdminCreateUser");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.CreateSubAccountTests.testSubscriptionAdminCreateAdmin";
            annotation.setDependsOnMethods(DependentMethods);
        }
	}
}
