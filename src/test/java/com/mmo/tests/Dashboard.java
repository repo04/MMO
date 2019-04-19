package com.mmo.tests;

import org.testng.annotations.Test;

import com.mmo.util.Actions;
import com.mmo.util.BaseClass;

public class Dashboard extends BaseClass{

	Actions a = new Actions();
    String downloadDefaultTemplates[] = new String[3];
	
	
	@Test
	public void testVerifyDashboard() throws Exception {
		a.verifyDashboard();
		downloadDefaultTemplates[0] = "CSV";
		downloadDefaultTemplates[1] = "SHP";
		downloadDefaultTemplates[2] = "TAB";
		a.downloadDefaultTemplatesAndVerify(downloadDefaultTemplates);
		//new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(By.id("refreshDashboard")));		
	}

}
