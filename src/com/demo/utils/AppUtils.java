package com.demo.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
 
public class AppUtils {
	
    public static void main(String[] args) {
    	String[] arr={"aa","bb"};
    	System.out.println(arr);
    	System.out.println(arr.toString());
    	System.out.println(Arrays.toString(arr));
    }
    
    public static String decode1(String s) {
    	String ds = null;
        try {
        	ds = URLDecoder.decode(s,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ds;
    }
	 
    public static String decode2(String s) {
    	String ds = null;
        try {
        	ds = URLDecoder.decode(URLDecoder.decode(s,"UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ds;
    }
}
