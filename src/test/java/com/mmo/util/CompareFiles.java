package com.mmo.util;

import org.testng.Reporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CompareFiles extends BaseClass{

	private Actions a = new Actions();
	String expFile;
	boolean areEqual = true;

	public void compareCSVFiles(String actualFile, String advanceGeocoding, String multiMatch) {
		BufferedReader reader1 = null;
		BufferedReader reader2 = null;
		try {
			reader1 = new BufferedReader(new FileReader(defaultDownloadPath + File.separator + actualFile + File.separator + actualFile + "SRTD.csv"));

			if (advanceGeocoding.equalsIgnoreCase("Increase Match Rate")) {
				expFile = "MR_BMSrtd.csv";
			} else if (advanceGeocoding.equalsIgnoreCase("Increase Accuracy")) {
				expFile = "ACC_BMSrtd.csv";
			} else if (advanceGeocoding.equalsIgnoreCase("Custom")) {
				expFile = "CUS_BMSrtd.csv";
			} else {
				if (multiMatch.equalsIgnoreCase("Accept None")) {
					expFile = "NONE_BMSrtd.csv";
				} else {
					expFile = "DEF_BMSrtd.csv";
				}
			}
			reader2 = new BufferedReader(new FileReader(directory.getCanonicalPath() + File.separator + "data" + File.separator +
					"benchMarkFiles" + File.separator + expFile));

			String line1 = reader1.readLine();

			String line2 = reader2.readLine();

			int lineNum = 1;

			while (line1 != null || line2 != null) {
				if (line1 == null || line2 == null) {
					areEqual = false;
					break;
				} else if (!line1.contentEquals(line2)) {
					areEqual = false;
					break;
				}

				line1 = reader1.readLine();
				line2 = reader2.readLine();
				lineNum++;
			}
			if (areEqual) {
				Reporter.log("Act/Exp " + actualFile + "/" + expFile + " Files have same content." + "<br/>", true);
			} else {
				Reporter.log("Two files have different content. They differ at line " + lineNum, true);
				Reporter.log("File1 has " + line1 + " and File2 has " + line2 + " at line " + lineNum, true);
				a.navigateToDashboard();
				System.out.println("EXP 1");
				u.illegalStateException("Act/Exp " + actualFile + "/" + expFile + " Files have different content");
			}
		}catch (IOException e) {
			//do nothing
		}finally {
			if(reader1 !=null ){
				try {
					reader1.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(reader2 !=null ){
				try {
					reader2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}