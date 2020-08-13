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
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.SignUp.testSignUpFreeUSUserAndCompleteEmailRegistration";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testSubscriptionAdminCreateUser".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminCreateUser");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.SignUp.testSignUpFreeUSUserAndCompleteEmailRegistration";
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

//        if ("testUploadIncorrectFilesAndCheckValidations".equals(testMethod.getName())) {
//            System.out.println("Inside testUploadIncorrectFilesAndCheckValidations");
//            DependentMethods = new String[1];
//            DependentMethods[0] = "testSAUploadFileConfigureAndStartGeocoding";
//            annotation.setDependsOnMethods(DependentMethods);
//            annotation.setAlwaysRun(true);
//        }

        if ("testSubscriptionAdminJobsVisibleCompletionAndVerifyDetails".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminJobsVisibleCompletionAndVerifyDetails");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.JobsBySATests.testSAUploadFileConfigureAndStartGeocoding";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testAdminsJobsVisibleCompletionAndVerifyDetails".equals(testMethod.getName())) {
            System.out.println("Inside testAdminsJobsVisibleCompletionAndVerifyDetails");
            DependentMethods = new String[2];
            DependentMethods[0] = "com.mmo.tests.JobExecutionTests.testAdmin1UploadFileConfigureAndStartGeocoding";
            DependentMethods[1] = "com.mmo.tests.JobExecutionTests.testAdmin2UploadFileConfigureAndStartGeocoding";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testUsersJobsVisibleCompletionAndVerifyDetails".equals(testMethod.getName())) {
            System.out.println("Inside testUsersJobsVisibleCompletionAndVerifyDetails");
            DependentMethods = new String[2];
            DependentMethods[0] = "com.mmo.tests.JobExecutionTests.testUser1UploadFileConfigureAndStartGeocoding";
            DependentMethods[1] = "com.mmo.tests.JobExecutionTests.testUser2UploadFileConfigureAndStartGeocoding";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testVerifyAllJobEmails".equals(testMethod.getName())) {
            System.out.println("Inside testVerifyAllJobEmails");
            DependentMethods = new String[3];
            DependentMethods[0] = "com.mmo.tests.JobCompletionTests.testSubscriptionAdminJobsVisibleCompletionAndVerifyDetails";
            DependentMethods[1] = "com.mmo.tests.JobCompletionTests.testAdminsJobsVisibleCompletionAndVerifyDetails";
            DependentMethods[2] = "com.mmo.tests.JobCompletionTests.testUsersJobsVisibleCompletionAndVerifyDetails";
            annotation.setDependsOnMethods(DependentMethods);
        }
    }
}
