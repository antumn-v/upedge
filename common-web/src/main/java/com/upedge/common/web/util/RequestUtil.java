package com.upedge.common.web.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestUtil {

	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		return request;
	}

	public static HttpServletResponse getResponse() {
		HttpServletResponse  response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
		return response;
	}

	public static String getWarehouseCode(){
		HttpServletRequest request = getRequest();
		String warehouseCode = request.getHeader("warehouseCode");
		return warehouseCode;
	}
		
}
