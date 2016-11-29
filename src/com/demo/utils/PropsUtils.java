package com.demo.utils;

import java.util.*;

import java.io.*;

/**
 * 公共属性文件读取类
 * @author Administrator
 *
 */
public class PropsUtils {
	
	private static Properties dbconfig = new Properties();
	private static Properties path = new Properties();
	static {
		try {
			dbconfig.load(PropsUtils.class.getResourceAsStream("/dbconfig.properties"));
		} catch (IOException e) {
			System.err.println("#ERROR# :dbconfig.properties not found!");
		}
	}
	public static String getDBProperty(String key){
		if(dbconfig.containsKey(key))return dbconfig.getProperty(key);
		else return "";
	}
    
	public static String getPathProperty(String key){
		if(path.containsKey(key))return path.getProperty(key);
		else return "";
	}

    public static void main(String[] args) {
    	System.out.println(getDBProperty("jdbc_url"));
	}
    
    
}
