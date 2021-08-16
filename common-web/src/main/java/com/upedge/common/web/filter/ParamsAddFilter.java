package com.upedge.common.web.filter;

import com.upedge.common.web.request.ParameterRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加请求参数DEMO，如果要修改参数则需要重新创建request
 * @author xunli
 */
//@WebFilter(urlPatterns = {"/subscriber/*","/user/*","/org/*"})
public class ParamsAddFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest)servletRequest;
	        HttpServletResponse response = (HttpServletResponse)servletResponse;

	        Map parameter = new HashMap(16);
	        parameter.put("createTime", new Date());
	        parameter.put("updateTime", new Date());
	        ParameterRequestWrapper wrapper = new ParameterRequestWrapper(request, parameter);
	        filterChain.doFilter(wrapper, response);
	        return;
	}
}