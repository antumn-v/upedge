package com.upedge.common.utils;

import org.apache.commons.lang3.StringUtils;

public class GenerateUtil {

	public static String getCamelCase(String str) {
		if(!str.contains("_")) {
			return str;
		}
		String[] strs = str.split("_");
		for(int i=1; i< strs.length; i++) {
			strs[i] = strs[i].substring(0, 1).toUpperCase()+strs[i].substring(1);
		}
		return StringUtils.join(strs, "");
	}
	
	public static String getClassName(String tableName) {
		String camelStr = getCamelCase(tableName);
		return camelStr.substring(0, 1).toUpperCase()+camelStr.substring(1);
	}
	
	public static String getMethodName(String type, String columnName) {
		return type+getClassName(columnName);
	}
	
	public static String getFieldName(String columnName) {
		String temp = getCamelCase(columnName);
		return temp.substring(0,1).toLowerCase()+temp.substring(1);
	}
	
	public static String getTenString(String str) {
		if(str.length() > 10) {
			return str;
		}
		else {
			while(str.length() < 10) {
				str += " ";
			}
			return str;
		}
	}
	
//	public static void main(String[] args) {
//		System.out.println(getCamelCase("djw_ere"));
//		System.out.println(getClassName("djw_ere"));
//		System.out.println(getMethodName("get","djw_ere"));
//		System.out.println(getFieldName("djw_ere"));
//		System.out.println(getFieldName("Djw_ere"));
//		System.out.println(getTenString("123").length());
//		System.out.println("|"+getTenString("123")+"|");
//		
//	}
}
