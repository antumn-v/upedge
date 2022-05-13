package com.upedge.common.web.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class RequestUtil {

	public static HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
		return request;
	}

	public static HttpServletResponse getResponse() {
		HttpServletResponse  response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();
		return response;
	}

	private static final String UNKNOWN = "unknown";
	private static final String LOCALHOST_IP = "127.0.0.1";
	// 客户端与服务器同为一台机器，获取的 ip 有时候是 ipv6 格式
	private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";
	private static final String SEPARATOR = ",";

	// 根据 HttpServletRequest 获取 IP
	public static String getIpAddress(HttpServletRequest request) {
		if (request == null) {
			return "unknown";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (LOCALHOST_IP.equalsIgnoreCase(ip) || LOCALHOST_IPV6.equalsIgnoreCase(ip)) {
				// 根据网卡取本机配置的 IP
				InetAddress iNet = null;
				try {
					iNet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				if (iNet != null)
					ip = iNet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，分割出第一个 IP
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(SEPARATOR) > 0) {
				ip = ip.substring(0, ip.indexOf(SEPARATOR));
			}
		}
		return LOCALHOST_IPV6.equals(ip) ? LOCALHOST_IP : ip;
	}

	public static String getWarehouseCode(){
		HttpServletRequest request = getRequest();
		String warehouseCode = request.getHeader("warehouseCode");
		if (StringUtils.isBlank(warehouseCode)){
			warehouseCode = "CNHZ";
		}
		return warehouseCode;
	}
		
}
