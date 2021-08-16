package com.upedge.common.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Filter重定向Demo，本例中不生效
 * 如果要启用，需要将WebFilter注解添加到类上
 * @author xunli
 *
 */
//@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class LoginFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
		try {
			HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
			HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
			String url = httpServletRequest.getRequestURL().substring(httpServletRequest.getContextPath().length());

			logger.info("获取到url请求{}", url);

			if (url.contains("**")) {
				filterChain.doFilter(httpServletRequest, httpServletResponse);
				return;
			}

			String[] filter = { "*", "*", "*", "*", "*" };
			for (String s : filter) {
				if (url.contains(s)) {
					filterChain.doFilter(httpServletRequest, httpServletResponse);
					return;
				}
			}

			HttpSession httpSession = httpServletRequest.getSession();
			if (httpSession.getAttribute("adminCode") != null) {
				logger.info("获取到session中的adminCode，放行");
				filterChain.doFilter(httpServletRequest, httpServletResponse);
			} else {
				logger.info("未获取到session中的adminCode，跳转登录页面");
				if (httpServletRequest.getHeader("x-requested-with") != null
						&& "XMLHttpRequest".equals(httpServletRequest.getHeader("x-requested-with"))) {
					httpServletResponse.setHeader("sessionstatus", "timeout");
					httpServletResponse.setStatus(403);
					httpServletResponse.addHeader("loginPath",
							httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":"
									+ httpServletRequest.getServerPort() + "/production");
					filterChain.doFilter(httpServletRequest, httpServletResponse);
					logger.info("获取到ajax请求,跳转登录页面");
					return;
				}
				httpServletResponse
						.sendRedirect(httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":"
								+ httpServletRequest.getServerPort() + "/production");
			}
		} catch (Exception e) {
			logger.error("拦截器报错:", e);
		}
	}

}
