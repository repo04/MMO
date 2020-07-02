package com.mmo.tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;

public class Dashboard extends BaseClass{

	String downloadDefaultTemplates[] = new String[6];
	private Actions a = new Actions();
	
    @BeforeClass(groups = {"prerequisite"})
	public void testVerifyDashboard() throws Exception {
		//a.verifyDashboard();
	}

	@Test
	public void testDownloadAllDefaultTemplates() throws Exception {
		downloadDefaultTemplates[0] = "FRWD CSV";
		downloadDefaultTemplates[1] = "FRWD SHP";
		downloadDefaultTemplates[2] = "FRWD TAB";
		downloadDefaultTemplates[3] = "RVRS CSV";
		downloadDefaultTemplates[4] = "RVRS SHP";
		downloadDefaultTemplates[5] = "RVRS TAB";
		a.downloadAllDefaultTemplates(downloadDefaultTemplates);
	}
	
	@AfterClass(groups = {"prerequisite"})
	public void testLogout() throws Exception {
		a.logOut();
	}
}
