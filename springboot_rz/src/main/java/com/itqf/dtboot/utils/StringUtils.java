package com.itqf.dtboot.utils;

public class StringUtils {

    public static  boolean isEmpty(String s){
        if (s==null){
            return true;// String s = null;
        }
        if (s.isEmpty()){//String s="";
            return true;
        }

        return false;
    }

    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }

}
