package com.coinwallet.common.util;

import java.util.regex.Pattern;

public class StringCheckUtil {

    public static boolean isPassword(String str, int min, int max) {
        String regex = "^(?![0-9A-Z]+$)(?![0-9a-z]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{"+min+","+max+"}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }
    public static boolean isAlphabetDigit(String str){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");
        return pattern.matcher(str).matches();
    }

    public static boolean isDigit(String str){
        Pattern pattern = Pattern.compile("[0-9]+");
        return pattern.matcher(str).matches();
    }

    public static boolean containsAlphabet(String str){
        String regex=".*[a-zA-Z]+.*";
        return Pattern.compile(regex).matcher(str).matches();
    }
    public static boolean isContainsLowAndUpperAlphabetAndDigit(String str,int minLength,int maxLength){
        String regex="^(?![0-9A-Z]+$)(?![0-9a-z]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{"+minLength+","+maxLength+"}$";
        return Pattern.compile(regex).matcher(str).matches();
    }
}
