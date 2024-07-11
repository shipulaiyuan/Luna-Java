package com.virtual.luna.common.base.utils;

import java.util.Arrays;
import java.util.List;

public class ListUtils {

    /**
     * 封装方法，用于将"[1,2,3]" 转化为 List
     * @param value
     * @return
     */
    public static List<String> splitValues(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty.");
        }

        value = value.substring(1, value.length() - 1);

        List<String> values = Arrays.asList(value.split(",\\s*"));

        return values;
    }
}
