package com.mmo.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class DataProviderUtility {

    /**
     * Given a variable list of @DataProvider results, generate a Cartesian product of available combination's.
     *
     * @param dataProviderData A vararg list of @DataProvider results
     * @return The cartesian product of available combinations.
     */
    public static Iterator<Object[]> cartesianProviderFrom(Object[][]... dataProviderData) {
        return cartesianProviderFrom(ImmutableList.copyOf(dataProviderData));
    }

    public static Object[][] appendOutFileNameToEachRow(Object[][] dataProviderData1, Object[][] dataProviderData2) {
        return appendOutFileName(dataProviderData1, dataProviderData2);
    }

    /**
     * Given a list of @DataProvider results, generate a Cartesian product of available combination's.
     *
     * @param dataProviderData A list of @DataProvider results
     * @return The cartesian product of available combinations.
     */
    private static Iterator<Object[]> cartesianProviderFrom(List<Object[][]> dataProviderData) {

        ImmutableList.Builder<Set<Object[]>> cartesianSets = ImmutableList.builder();
        for (Object[][] objects : dataProviderData) {
            cartesianSets.add(Sets.newHashSet(objects));
        }

        Set<List<Object[]>> cartesianData = Sets.cartesianProduct(cartesianSets.build());
        List<Object[]> data = Lists.newArrayList();

        for (List<Object[]> objects : cartesianData) {
            Object[] mergedArray = flattenObjectArray(objects);
            data.add(mergedArray);
        }

        return data.iterator();
    }

    private static Object[] flattenObjectArray(List<Object[]> arrays) {

        int len = 0;
        for (Object[] array : arrays) {
            len += array.length;
        }

        int index = 0;

        Object[] mergedArray = new Object[len];

        for (Object[] array : arrays) {
            for (int i = 0; i < array.length; i++) {
                mergedArray[index++] = array[i];
            }
        }

        return mergedArray;
    }

    private static Object[][] appendOutFileName(Object[][] jobDetails, Object[][] outFileNames) {
        int p = 0;
        int q;
        for (Object[] job : jobDetails) {
            p = job.length;
        }
        q = p + 1;

        Object[][] c = new Object[jobDetails.length][q];
        for (int i = 0; i < jobDetails.length; i++) {
            for (int j = 0; j < q; j++) {
                if (j < p) {
                    c[i][j] = jobDetails[i][j];
                } else {
                    c[i][j] = outFileNames[i][0];
                }
            }
        }

        for (Object[] row : c)

            // converting each row as string
            // and then printing in a separate line
            System.out.println(Arrays.toString(row));

        return c;
    }

    private static Iterator<Object[]> append1DArrayVertically(Object[][]... dataProviderData) {
        int p = 0;
        for (Object[] object : dataProviderData) {
            p = p + object.length;
        }

        System.out.println("P LEN: " + p +"\n");
        Object[][] tabArray = new String[0][p];
        List<Object[]> data = Lists.newArrayList();

        int ci = 0;
        for (Object[][] object : dataProviderData) {
            for (int j = 0; j < p; j++) {
                tabArray[ci][j] = object[0][j];
                //System.out.println("["+ci+"]["+j+"]: " + tabArray[ci][j].toString() +"\n");
            }
            ci++;
        }
        //return tabArray;
        return data.iterator();
    }

    // Append 2D Array Vertically
    public static Object[][] append2DArrayVertically(Object[][]... dataProviderData) {
        int x = 0;
        int y = 0;
        for (Object[] object : dataProviderData) {
            x = x + object.length;
            y =  object.length;
        }
        Object[][] tabArray;

        tabArray = new String[x][3];

        int ci = 0;
        for (Object[][] object : dataProviderData) {
            for (int i = 0; i < y; i++, ci++) {
                int cj = 0;
                for (int j = 0; j < 3; j++, cj++) {
                    tabArray[ci][cj] = object[i][j];
                    //System.out.println("["+ci+"]["+cj+"]: " + tabArray[ci][cj].toString() +"\n");
                }
            }
        }
        return tabArray;
    }

    // Append 2D Array Vertically
    public static Object[][] append2DJobDetailsArrayVertically(Object[][]... dataProviderData) {
        int x = 0;
        int y;
        int z = 0;
        for (Object[] object : dataProviderData) {
            x = x + object.length;
        }

        for (Object[][] object : dataProviderData) {
            z = object[0].length;
        }

        Object[][] tabArray;

        tabArray = new String[x][z];

        int ci = 0;
        for (Object[][] object : dataProviderData) {
            y =  object.length;
            for (int i = 0; i < y; i++, ci++) {
                int cj = 0;
                for (int j = 0; j < object[i].length; j++, cj++) {
                    tabArray[ci][cj] = object[i][j];
                    //System.out.println("["+ci+"]["+cj+"]: " + tabArray[ci][cj].toString() +"\n");
                }
            }
        }
        return tabArray;
    }

    /*for (int i = 0; i < allSA.length; i++) { //this equals to the row in our matrix.
        for (int j = 0; j < allSA[i].length; j++) { //this equals to the column in each row.
            System.out.print(allSA[i][j] + " ");
        }
        System.out.println(); //change line on console as row comes to end in the matrix.
    }*/
}