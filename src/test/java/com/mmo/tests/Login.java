package com.mmo.tests;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;

public class Login extends BaseClass {
	private Actions a = new Actions();

	@BeforeClass(groups = {"prerequisite"})
	public void testPreLogIn(ITestContext context) throws Exception {
		a.navigateToLogin();
		a.verifyLoginPage();
	}
	
	@Test
	public void testLogin() throws Exception {
		//a.login("someshb04+geo2map3@gmail.com");
		a.login("autommopb+automation5ksubuser@gmail.com");
		//a.login("mapmarkerprd+v2@gmail.com");
		//a.verifyCreatedUser();
		//a.navigateToBillingPlan();
	}	
	
	@AfterClass(groups = {"prerequisite"})
	public void testLogout() throws Exception {
		//a.logOut();
	}
}