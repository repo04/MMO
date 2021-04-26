package com.mmo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//https://stackoverflow.com/questions/1894060/sort-one-column-of-data-in-a-csv-file-in-ascending-order-in-java
public class SortCSV extends BaseClass {

    public void sortCSVFile(String outFileName) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(defaultDownloadPath + File.separator + outFileName + File.separator + outFileName + ".csv"));
        //public static void main(String[] args) throws Exception {
        //BufferedReader reader = new BufferedReader(new FileReader("D:\\MMOnline\\Automation\\workspace\\tests\\data\\benchMarkFiles\\InpTesting5_2_MR_BM.csv"));
        Map<String, List<String>> map = new TreeMap<String, List<String>>();
        String line;
        while ((line = reader.readLine()) != null) {
            //String key = getField(line);
            String key = line.split(";")[0];
            List<String> l = map.get(key);
            if (l == null) {
                l = new LinkedList<String>();
                map.put(key, l);
            }
            l.add(line);
        }
        reader.close();
        FileWriter writer = new FileWriter(defaultDownloadPath + File.separator + outFileName + File.separator + outFileName + "SRTD.csv");
        //FileWriter writer = new FileWriter("D:\\MMOnline\\Automation\\workspace\\tests\\data\\benchMarkFiles\\InpTesting5_2_MR_BMSrtd.csv");
        for (List<String> list : map.values()) {
            for (String val : list) {
                writer.write(val);
                writer.write("\n");
            }
        }
        writer.close();
    }
}
