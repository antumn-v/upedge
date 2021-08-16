package com.upedge.common.utils;

import com.upedge.common.constant.Constant;

public class NumberUtil {
	
	public static String getThirtySix(Object number) {
		if(number instanceof Integer) {
			return Integer.toString((Integer)number, Constant.SYSTEM_36);
		}
		else if(number instanceof Long) {
			return Long.toString((Long)number, Constant.SYSTEM_36);
		}
		return null;
	}
	
	public static String getThirtySixWithPosition(Object number, int p) {
		String tstr = getThirtySix(number);
		while(tstr.length() < p) {
			tstr = "0"+tstr;
		}
		return tstr;
	}
}
