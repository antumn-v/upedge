package com.upedge.common.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public abstract class BaseController {

	@Autowired
	private HttpServletRequest request;
	
	/**
	 * 所有继承本类的Controller 再执行每个方法之前都会先执行 ModelAttribbute注释的方法，
	 * 不过本方法是非线程安全的
	 * @throws IOException
	 */
	@ModelAttribute
    public void pre() throws IOException {
    }
}
