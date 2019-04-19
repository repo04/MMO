package com.mmo.util;

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

    /**
     * Given a list of @DataProvider results, generate a Cartesian product of available combination's.
     *
     * @param dataProviderData A list of @DataProvider results
     * @return The cartesian product of available combinations.
     */
    public static Iterator<Object[]> cartesianProviderFrom(List<Object[][]> dataProviderData) {

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

}