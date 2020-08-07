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

        if ("testAdmin1UploadFileConfigureAndStartGeocoding".equals(testMethod.getName()) || "testUser1UploadFileConfigureAndStartGeocoding".equals(testMethod.getName())
            || "testAdmin2UploadFileConfigureAndStartGeocoding".equals(testMethod.getName()) || "testUser2UploadFileConfigureAndStartGeocoding".equals(testMethod.getName())) {
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.Job.testSAUploadFileConfigureAndStartGeocoding";
            annotation.setDependsOnMethods(DependentMethods);
        }
		
		if ("testWaitForJobToGetCompleteDownloadAndCompare".equals(testMethod.getName())) {
            System.out.println("Inside testWaitForJobToGetCompleteDownloadAndCompare");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.Job.testSAUploadFileConfigureAndStartGeocoding";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testJobDetails".equals(testMethod.getName())) {
            System.out.println("Inside testJobDetails");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.Job.testWaitForJobToGetCompleteDownloadAndCompare";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testSubscriptionAdminVerifyDetails".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminVerifyDetails");
            DependentMethods = new String[4];
            DependentMethods[0] = "com.mmo.tests.SignUp.testSignUpFreeUSUserAndCompleteEmailRegistration";
            DependentMethods[1] = "com.mmo.tests.SignUp.testSignUpFreeNonUSUserAndCompleteEmailRegistration";
            DependentMethods[2] = "com.mmo.tests.SignUp.testSignUpPaid5kUserAndCompleteEmailRegistration";
            DependentMethods[3] = "com.mmo.tests.SignUp.testSignUpPaidProfUserAndCompleteEmailRegistration";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testSubscriptionAdminUpdateAndVerifyCardDetails".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminUpdateAndVerifyCardDetails");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.SubscriptionAdminVerifyDetailTests.testSubscriptionAdminVerifyDetails";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testSubscriptionAdminCreateAdmin".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminCreateAdmin");
            DependentMethods = new String[4];
            DependentMethods[0] = "com.mmo.tests.SignUp.testSignUpFreeUSUserAndCompleteEmailRegistration";
            DependentMethods[1] = "com.mmo.tests.SignUp.testSignUpFreeNonUSUserAndCompleteEmailRegistration";
            DependentMethods[2] = "com.mmo.tests.SignUp.testSignUpPaid5kUserAndCompleteEmailRegistration";
            DependentMethods[3] = "com.mmo.tests.SignUp.testSignUpPaidProfUserAndCompleteEmailRegistration";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testSubscriptionAdminCreateUser".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminCreateUser");
            DependentMethods = new String[4];
            DependentMethods[0] = "com.mmo.tests.SignUp.testSignUpFreeUSUserAndCompleteEmailRegistration";
            DependentMethods[1] = "com.mmo.tests.SignUp.testSignUpFreeNonUSUserAndCompleteEmailRegistration";
            DependentMethods[2] = "com.mmo.tests.SignUp.testSignUpPaid5kUserAndCompleteEmailRegistration";
            DependentMethods[3] = "com.mmo.tests.SignUp.testSignUpPaidProfUserAndCompleteEmailRegistration";
            annotation.setDependsOnMethods(DependentMethods);
        }

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

        if ("testAdminVerifyDetails".equals(testMethod.getName())) {
            System.out.println("Inside testAdminVerifyDetails");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.CreateSubAccountTests.testAdminCreateAdmin";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testUserVerifyDetails".equals(testMethod.getName())) {
            System.out.println("Inside testUserVerifyDetails");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.CreateSubAccountTests.testAdminCreateUser";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testDegrade5kToFreePlanAndCheckEmail".equals(testMethod.getName())) {
            System.out.println("Inside testDegrade5kToFreePlanAndCheckEmail");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.UpgradeTests.testUpgradeFreeTo5kPlan";
            annotation.setDependsOnMethods(DependentMethods);
        }
    }
}
