package com.mmo.util;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.testng.Assert;
import org.testng.Reporter;

/**
 * https://www.codejava.net/java-se/file-io/programmatically-extract-a-zip-file-using-java
 * 
 * This utility iterates through a ZIP file without actually extracting it
 * 
 * @author www.codejava.net
 *
 */
public class UnzipUtility extends BaseClass {

	private Actions a = new Actions();
	/**
	 * Iterates through a ZIP file without actually extracting it
	 * 
	 * @param zipActualFileName
	 * @param outFileFormat
	 * @throws IOException
	 */
	public boolean unzip(String zipActualFileName, String outFileFormat) throws IOException {
		String wholePath = defaultDownloadPath + File.separator + zipActualFileName;
		System.out.print("wholePath: " + wholePath + "\n");
		ZipFile zip_file = new ZipFile(wholePath);
		ArrayList<String> zipExtEntries = new ArrayList<>();
		ArrayList<String> zipExtFound = new ArrayList<>();

		if(outFileFormat.equalsIgnoreCase("shp")){
			zipExtEntries.add(".dbf");
			zipExtEntries.add(".prj");
			zipExtEntries.add(".shx");
			zipExtEntries.add(".shp");
		}else if(outFileFormat.equalsIgnoreCase("tab"))
		{
			zipExtEntries.add(".dat");
			zipExtEntries.add(".id");
			zipExtEntries.add(".map");
			zipExtEntries.add(".tab");
		}else{
			zipExtEntries.add(".csv");
		}

		Assert.assertTrue(zip_file.size() == zipExtEntries.size(),"Zip entries differ in size");

		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(wholePath));
		ZipEntry entry = zipIn.getNextEntry();
		String findFile = zipActualFileName.substring(0, zipActualFileName.indexOf(".")) + "."
				+ outFileFormat.toLowerCase();
		System.out.print("findFile: " + findFile + "\n");

		Boolean fileFound = false;

		// iterates over entries in the zip file
		while (entry != null) {
			System.out.print("Entry: " + entry.getName() + "\n");
			for (String zipExtEntry : zipExtEntries){
				if (entry.getName().contains(zipExtEntry)) {
					System.out.print("Add: " + zipExtEntry + "\n");
					zipExtFound.add(zipExtEntry);
				}
				if (!fileFound) {
					if (entry.getName().contentEquals(findFile)) {
						System.out.print("File found: " + findFile + "\n");
						fileFound = true;
						Reporter.log("File found in ZIP folder: " + findFile + "\n");
						if(outFileFormat.equalsIgnoreCase("tab")){
							verifyDataTypeLength(wholePath.substring(0, wholePath.indexOf("."))
									 + File.separator + entry.getName());
						}
					}
				}
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
		zipExtEntries.removeAll(zipExtFound);
		System.out.print("zipExtEntries size: " + zipExtEntries.size() + "\n");
		if(zipExtEntries.size() != 0){
			a.navigateToDashboard();
			u.illegalStateException("Expected zip extension not found in Output ZIP folder");
		}
		return fileFound;
	}

	public void verifyDataTypeLength(String fileWithPath) {
		System.out.println("fileWithPath: " + fileWithPath);

		ArrayList<String> expTexts = new ArrayList<>();

		expTexts.add("Longitude char (24) ;");
		expTexts.add("Latitude char (9) ;");
		expTexts.add("Country char (3) ;");

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileWithPath));
			String line = reader.readLine();

			while (line != null) {
				System.out.println("LINE: " + line);
				for (int i = 0; i < expTexts.size(); i++) {
					if (line.contains(expTexts.get(i))) {
						System.out.println("LINE CONTAIN: " + expTexts.get(i));
						expTexts.remove(expTexts.get(i));
						System.out.println("SIZE: " + expTexts.size());
						break;
					}
				}
				line = reader.readLine();
				if (expTexts.size() == 0) {
					break;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (expTexts.size() != 0) {
			a.navigateToDashboard();
			u.illegalStateException("Mismatch in DataType Length");
		}
	}
}