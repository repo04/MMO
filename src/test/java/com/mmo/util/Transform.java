package com.mmo.util;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Transform implements IAnnotationTransformer {
	
	String DependentMethods[] = null;

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// TODO Auto-generated method stub

        if ("testSubscriptionAdminVerifyDetails".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminVerifyDetails");
            DependentMethods = new String[2];
            DependentMethods[0] = "com.mmo.tests.SignUpTests.testSignUpFreeUSUserAndCompleteEmailRegistration";
//            DependentMethods[1] = "com.mmo.tests.SignUpTests.testSignUpFreeNonUSUserAndCompleteEmailRegistration";
            DependentMethods[1] = "com.mmo.tests.SignUpTests.testSignUpPaid5kUserAndCompleteEmailRegistration";
//            DependentMethods[3] = "com.mmo.tests.SignUpTests.testSignUpPaidProfUserAndCompleteEmailRegistration";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testSubscriptionAdminCreateAdmin".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminCreateAdmin");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.SignUpTests.testSignUpFreeUSUserAndCompleteEmailRegistration";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testSubscriptionAdminCreateUser".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminCreateUser");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.SignUpTests.testSignUpFreeUSUserAndCompleteEmailRegistration";
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

        if ("testSubscriptionAdminUploadFileConfigureAndStartGeocoding".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminUploadFileConfigureAndStartGeocoding");
            DependentMethods = new String[2];
            DependentMethods[0] = "com.mmo.tests.SignUpTests.testSignUpFreeUSUserAndCompleteEmailRegistration";
            DependentMethods[1] = "testJobsBySALogin";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testUploadIncorrectFilesAndCheckValidations".equals(testMethod.getName())) {
            System.out.println("Inside testUploadIncorrectFilesAndCheckValidations");
            DependentMethods = new String[1];
            DependentMethods[0] = "testSubscriptionAdminUploadFileConfigureAndStartGeocoding";
            annotation.setDependsOnMethods(DependentMethods);
            annotation.setAlwaysRun(true);
        }

        if ("testSubscriptionAdminVerifyJobsFailureEmails".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminVerifyJobsFailureEmails");
            DependentMethods = new String[1];
            DependentMethods[0] = "testUploadIncorrectFilesAndCheckValidations";
            annotation.setDependsOnMethods(DependentMethods);
            annotation.setAlwaysRun(true);
        }

        if ("testSubscriptionAdminVerifyJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminVerifyJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength");
            DependentMethods = new String[1];
            //DependentMethods[0] = "testUploadIncorrectFilesAndCheckValidations";
            DependentMethods[0] = "testSubscriptionAdminUploadFileConfigureAndStartGeocoding";
            annotation.setDependsOnMethods(DependentMethods);
            annotation.setAlwaysRun(true);
        }

        if ("testSubscriptionAdminVerifyJobCompleteEmailAndAccessDetailsDirectly".equals(testMethod.getName())) {
            System.out.println("Inside testSubscriptionAdminVerifyJobCompleteEmailAndAccessDetailsDirectly");
            DependentMethods = new String[1];
            DependentMethods[0] = "testSubscriptionAdminVerifyJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testAdmin1UploadFileConfigureAndStartGeocoding".equals(testMethod.getName()) || "testAdmin2UploadFileConfigureAndStartGeocoding".equals(testMethod.getName())) {
            System.out.println("Inside testAdmin1/2JobsVisibleCompletionAndVerifyDetails");
            DependentMethods = new String[2];
            DependentMethods[0] = "com.mmo.tests.CreateSubAccountTests.testSubscriptionAdminCreateAdmin";
            DependentMethods[1] = "com.mmo.tests.CreateSubAccountTests.testAdminCreateAdmin";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testAdminsVerifyJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength".equals(testMethod.getName())) {
            System.out.println("Inside testAdminsVerifyJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength");
            DependentMethods = new String[2];
            DependentMethods[0] = "com.mmo.tests.JobExecutionBySubAccountTests.testAdmin1UploadFileConfigureAndStartGeocoding";
            DependentMethods[1] = "com.mmo.tests.JobExecutionBySubAccountTests.testAdmin2UploadFileConfigureAndStartGeocoding";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testUser1UploadFileConfigureAndStartGeocoding".equals(testMethod.getName()) || "testUser2UploadFileConfigureAndStartGeocoding".equals(testMethod.getName())) {
            System.out.println("Inside testUser1/2UploadFileConfigureAndStartGeocoding");
            DependentMethods = new String[2];
            DependentMethods[0] = "com.mmo.tests.CreateSubAccountTests.testSubscriptionAdminCreateUser";
            DependentMethods[1] = "com.mmo.tests.CreateSubAccountTests.testAdminCreateUser";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testUsersJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength".equals(testMethod.getName())) {
            System.out.println("Inside testUsersJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength");
            DependentMethods = new String[2];
            DependentMethods[0] = "com.mmo.tests.JobExecutionBySubAccountTests.testUser1UploadFileConfigureAndStartGeocoding";
            DependentMethods[1] = "com.mmo.tests.JobExecutionBySubAccountTests.testUser2UploadFileConfigureAndStartGeocoding";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testSubUsersVerifyJobCompleteEmail".equals(testMethod.getName())) {
            System.out.println("Inside testSubUsersVerifyJobCompleteEmail");
            DependentMethods = new String[2];
            DependentMethods[0] = "testAdminsVerifyJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength";
            DependentMethods[1] = "testUsersJobsVisibleCompletionDetailsDownloadCheckExtensionsAndDataTypeLength";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testUpgradeFreeTo5kPlanAndCheckEmail".equals(testMethod.getName())) {
            System.out.println("Inside testUpgradeFreeTo5kPlanAndCheckEmail");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.SignUpTests.testSignUpFreeUSUserAndCompleteEmailRegistration";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testExecuteJobAfterUpgrade".equals(testMethod.getName())) {
            System.out.println("Inside testExecuteJobAfterUpgrade");
            DependentMethods = new String[1];
            DependentMethods[0] = "testUpgradeFreeTo5kPlanAndCheckEmail";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testUpgrade5kToProfPlanAndCheckEmail".equals(testMethod.getName())) {
            System.out.println("Inside testUpgrade5kToProfPlan");
            DependentMethods = new String[1];
            DependentMethods[0] = "testExecuteJobAfterUpgrade";
            annotation.setDependsOnMethods(DependentMethods);
            annotation.setAlwaysRun(true);
        }

        if ("testDegrade5kToFreePlanAndCheckEmail".equals(testMethod.getName())) {
            System.out.println("Inside testDegrade5kToFreePlanAndCheckEmail");
            DependentMethods = new String[1];
            DependentMethods[0] = "com.mmo.tests.SignUpTests.testSignUpPaid5kUserAndCompleteEmailRegistration";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testExecuteJobAfterDegrade".equals(testMethod.getName())) {
            System.out.println("Inside testExecuteJobAfterDegrade");
            DependentMethods = new String[1];
            DependentMethods[0] = "testDegrade5kToFreePlanAndCheckEmail";
            annotation.setDependsOnMethods(DependentMethods);
        }

        if ("testAdmin2DeleteAdmin1".equals(testMethod.getName()) || "testAdmin2DeleteUser2".equals(testMethod.getName())
            || "testAdmin2DeleteUser1Job".equals(testMethod.getName())) {
            DependentMethods = new String[1];
            DependentMethods[0] = "testDeleteLogin";
            annotation.setDependsOnMethods(DependentMethods);
        }
    }
}