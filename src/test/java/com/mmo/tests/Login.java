package com.mmo.tests;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;

public class Login extends BaseClass{

	Actions a = new Actions();

	@BeforeClass(groups = {"prerequisite"})
	public void testLogIn(ITestContext context) throws Exception {
		a.navigateToLogin();		
	}

	@Test
	public void testLogin() throws Exception {
		//a.login("someshb04+free@gmail.com");
		//a.login("autoMMOPb+FreeUS110419141114@gmail.com");
		a.login("autoMMOPb+FreeUS110419124614@gmail.com");
		//a.login("someshb04+geo2map3@gmail.com");
	}	
	
	@AfterClass(groups = {"prerequisite"})
	public void testLogout() throws Exception {
		//a.logOut();
	}
}
