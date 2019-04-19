package com.mmo.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;

public class Email extends BaseClass{
	
	Actions a = new Actions();
	
	@BeforeClass(groups = {"prerequisite"})
    public void testEmailLogIn() throws Exception {
		a.navigateToEmail();
		a.emailLogin();        
    }
	
	/**
	 * 
	 * @param signUpUserID
	 * @param signUpFirstName
	 * @param signUpSecondName
	 * @throws Exception
	 */
	@Test(dataProvider = "FreeUSUserDetails", dataProviderClass = SignUp.class)
    public void testSignUpFreeUSUserCompleteRegisteration(String signUpUserID, String signUpFirstName, String signUpSecondName) throws Exception {
		a.signUpUserCompleteRegisteration(signUpUserID, signUpFirstName, signUpSecondName);        
    }
	
	/**
	 * 
	 * @param signUpUserID
	 * @param signUpFirstName
	 * @param signUpSecondName
	 * @throws Exception
	 */
	//@Test(dataProvider = "FreeNonUSUserDetails", dataProviderClass = SignUp.class)
    public void testSignUpNonFreeUsUserCompleteRegisteration(String signUpUserID, String signUpFirstName, String signUpSecondName) throws Exception {
		a.signUpUserCompleteRegisteration(signUpUserID, signUpFirstName, signUpSecondName);        
    }
	
	/**
	 * 
	 * @param signUpUserID
	 * @param signUpFirstName
	 * @param signUpSecondName
	 * @throws Exception
	 */
	//@Test(dataProvider = "Paid5kUserDetails", dataProviderClass = SignUp.class)
    public void testSignUpPaid5kUserCompleteRegisteration(String signUpUserID, String signUpFirstName, String signUpSecondName) throws Exception {
		a.signUpUserCompleteRegisteration(signUpUserID, signUpFirstName, signUpSecondName);        
    }
	
	/**
	 * 
	 * @param signUpUserID
	 * @param signUpFirstName
	 * @param signUpSecondName
	 * @throws Exception
	 */
	//@Test(dataProvider = "ProfUserDetails", dataProviderClass = SignUp.class)
    public void testSignUpProfUserCompleteRegisteration(String signUpUserID, String signUpFirstName, String signUpSecondName) throws Exception {
		a.signUpUserCompleteRegisteration(signUpUserID, signUpFirstName, signUpSecondName);        
    }
	
	
	/**
     * The annotated method will be run after all the test methods in the
     * current class have been run, User logsOut
     *
     * @throws Exception
     */
    @AfterClass(groups = {"prerequisite"})
    public void testTeacherEmailLogOut() throws Exception {
    	//Utility.userEmailLogOut(driver);        
    }
}
