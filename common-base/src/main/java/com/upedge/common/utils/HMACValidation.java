package com.upedge.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HMACValidation {

	static String secret="f0d4606886cc4a1cd0dbdf00a983a729";



	
	public static void main(String[] args) {
//		String url = "https://www.baidu.com/?hmac=9251efbc7d90ce6ea35a34a4a60313d6f5b5e3bc05d8d158f8f83ba7e9d62768&shop=senboshop.myshopify.com&timestamp=1545358041";
		String url="https://localhost:8443/auth?hmac=57121a7b4617ef0d1fba551db67e8dc59c05b5b30f2ba37cf772c572d5a51010&shop=nbcfamily.myshopify.com&timestamp=1545395354";
		String[] urlInfos = url.split("\\?");
		String[] params = urlInfos[1].split("&");
		List<String> paramList = new ArrayList<String>();
		String hmac = null;
		for(String param : params) {
			if(!param.startsWith("hmac")) {
				paramList.add(param);
			}
			else {
				hmac = param.split("=")[1];
			}
		}
		Valicate(hmac,paramList,"e4c7f6bb4148ee27bbd9c302611c0fe3");
	}

	public static Boolean Valicate(String hmac, List<String> paramList, String secret) {
		Collections.sort(paramList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String str1=(String) o1;
				String str2=(String) o2;
		        if (str1.compareToIgnoreCase(str2)<0){  
		            return -1;  
		        }  
		        return 1;  
			}
		}); 
		
		String paramStr = StringUtils.join(paramList.toArray(), "&");
		
		String code =EncryptUtil.sha256_HMAC(paramStr,secret);
		return hmac.equalsIgnoreCase(code);
	}

	public static String token(String hmac, List<String> paramList, String secret) {
		Collections.sort(paramList, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String str1=(String) o1;
				String str2=(String) o2;
		        if (str1.compareToIgnoreCase(str2)<0){
		            return -1;
		        }
		        return 1;
			}
		});

		String paramStr = StringUtils.join(paramList.toArray(), "&");

		String code =EncryptUtil.sha256_HMAC(paramStr,secret);
		if(hmac.equalsIgnoreCase(code)){
			return paramStr;
		}
		return null;

	}
}
