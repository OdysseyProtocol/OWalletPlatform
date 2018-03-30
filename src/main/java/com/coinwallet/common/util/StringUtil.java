package com.coinwallet.common.util;

public   class StringUtil {
 
    public static boolean isEmpty(String value) {
        return value == null || "".equals(value.trim());
    }
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
 
    public static boolean isNumber(String sourceString) {
        if (isEmpty(sourceString))
            return false;
        char[] sourceChar = sourceString.toCharArray();
        for (int i = 0; i < sourceChar.length; i++)
            if ((sourceChar[i] < '0') || (sourceChar[i] > '9'))
                return false;
        return true;
    }
}