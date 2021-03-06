package com.mmo.util;

import org.testng.Assert;
import org.testng.Reporter;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

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
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Iterates through a ZIP file without actually extracting it
	 * 
	 * @param zipActualFileName
	 * @param outFileFormat
	 * @throws IOException
	 */
	public boolean unzip(String zipActualFileName, String outFileFormat) throws IOException {
		String zipFilePath = defaultDownloadPath + File.separator + zipActualFileName;
		System.out.print("zipPath: " + zipFilePath + "\n");
		String destDirectory = defaultDownloadPath + File.separator +
				zipActualFileName.substring(0, zipActualFileName.indexOf("."));
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		ZipFile zipActEntries = new ZipFile(zipFilePath);
		ArrayList<String> zipExtEntries = new ArrayList<>();
		ArrayList<String> zipExtFound = new ArrayList<>();

		if(outFileFormat.equalsIgnoreCase("shp")){
			zipExtEntries.add(".cpg");
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

		Assert.assertTrue(zipActEntries.size() == zipExtEntries.size(),"Zip entries differ in size, exp: " + zipExtEntries.size() +
				" whereas act: " + zipActEntries.size());

		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		String findFile = zipActualFileName.substring(0, zipActualFileName.indexOf(".")) + "." + outFileFormat.toLowerCase();
		System.out.print("findFile: " + findFile + "\n");

		Boolean fileFound = false;

		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			extractFile(zipIn, filePath);
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
						Reporter.log("File found in ZIP folder: " + findFile  + "<br/>", true);
						if(outFileFormat.equalsIgnoreCase("tab")){
							if(findFile.contains("RevIn27700_NonExt") || findFile.contains("DPVwoSeedLess49")){
								verifyDataTypeLength(filePath);
								Reporter.log("Subscription Admin downloaded & verified dataTypeLength for output file: " +
										findFile.substring(0, zipActualFileName.indexOf(".")) + "<br/>", true);
							}
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
			u.illegalStateException("Expected zip extension's not found in Output ZIP folder");
		}
		zipIn = null;
		return fileFound;
	}

	public void verifyDataTypeLength(String fileWithPath) {
		System.out.print("INSIDE VERIFY DATA TYPE LENGTH" +  "\n");
		System.out.println("fileWithPath: " + fileWithPath);

		ArrayList<String> expTexts = new ArrayList<>();

		if(fileWithPath.contains("RevIn27700_NonExt")){
			expTexts.add("S_No_ integer ;");
			expTexts.add("Longitude float ;");
			expTexts.add("Latitude char (18) ;");
			expTexts.add("inp_country char (3) ;");
		}else if(fileWithPath.contains("DPVwoSeedLess49")){
			expTexts.add("S_No_ integer ;");
			expTexts.add("Add char (32) ;");
			expTexts.add("Cit char (13) ;");
			expTexts.add("Sta char (2) ;");
			expTexts.add("Pos integer ;");
			expTexts.add("Ctr char (3) ;");
		}

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

	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
}