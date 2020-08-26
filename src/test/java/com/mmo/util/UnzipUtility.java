package com.mmo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

	/**
	 * Iterates through a ZIP file without actually extracting it
	 * 
	 * @param zipActualFileName
	 * @param outFileFormat
	 * @throws IOException
	 */
	public boolean unzip(String zipActualFileName, String outFileFormat) throws IOException {
		String wholePath = defaultDownloadPath + File.separator + zipActualFileName;
		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(wholePath));
		ZipEntry entry = zipIn.getNextEntry();
		String findFile = zipActualFileName.substring(0, zipActualFileName.indexOf(".")) + "."
				+ outFileFormat.toLowerCase();
		Boolean fileFound = false;

		// iterates over entries in the zip file
		while (entry != null) {
			System.out.print("Entry: " + entry.getName() + "\n");
			if (entry.getName().contentEquals(findFile)) {
				fileFound = true;
				Reporter.log("File found in ZIP folder: " + findFile + "\n");
				break;
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
		return fileFound;
	}
}