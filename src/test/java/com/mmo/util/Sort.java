package com.mmo.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//https://javaconceptoftheday.com/how-to-sort-a-text-file-in-java/

class sortFile {

	int sNo;
	String restData;

	public sortFile(int sNo, String restData) {
		this.sNo = sNo;
		this.restData = restData;
	}
}

//nameCompare Class to compare the names
class nameCompare implements Comparator<sortFile> {
	@Override
	public int compare(sortFile s1, sortFile s2) {
		return s1.restData.compareTo(s2.restData);
	}
}

//marksCompare Class to compare the marks
class marksCompare implements Comparator<sortFile> {
	@Override
	public int compare(sortFile s1, sortFile s2) {
		return s2.sNo - s1.sNo;
	}
}

public class Sort {
	public static void main(String[] args) throws IOException {
		// Creating BufferedReader object to read the input text file
		BufferedReader reader = new BufferedReader(
				new FileReader("D:/MMOnline/Automation/workspace/tests/data/defaultTemplates/test.txt"));

		// Creating ArrayList to hold Student objects
		ArrayList<sortFile> sortFileRecords = new ArrayList<sortFile>();

		// Reading Student records one by one
		String currentLine = reader.readLine();

		int x = 0;
		String firstLine = null;
		while (currentLine != null) {
			if (x == 0) {
				firstLine = currentLine;
			}else{
				int index = currentLine.indexOf(",");
				int sNo = Integer.valueOf(currentLine.substring(0, index));
				String restData = currentLine.substring(index, currentLine.length());

				// Creating sortFile object for every student record and adding it to ArrayList
				sortFileRecords.add(new sortFile(sNo, restData));				
			}
			x++;
			currentLine = reader.readLine();
		}

		// Sorting ArrayList studentRecords based on marks
		Collections.sort(sortFileRecords, new marksCompare());

		// Creating BufferedWriter object to write into output text file
		BufferedWriter writer = new BufferedWriter(
				new FileWriter("D:/MMOnline/Automation/workspace/tests/data/defaultTemplates/output1.txt"));

		// Writing every studentRecords into output text file
		writer.write(firstLine);
		writer.newLine();
		for (sortFile student : sortFileRecords) {
			writer.write(student.sNo + student.restData);				
			writer.newLine();
		}

		// Closing the resources
		reader.close();
		writer.close();
	}
}