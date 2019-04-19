package com.mmo.tests;

import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;

public class Home extends BaseClass {
  
  Actions a = new Actions();

  @BeforeClass(groups = {"prerequisite"})
  public void testLogIn(ITestContext context) throws Exception {
	  //a.login("someshb04+free@gmail.com");
  }

  @Test
  public void testHome() throws Exception {
	ip.isElementClickableByXpath(driver, xpv.getTokenValue("homePageSignIn"), 60);
	a.home();	    
  }

  @AfterClass(groups = {"prerequisite"})
  public void testLogout() throws Exception {
	  //a.logOut();
  }  
  
}
