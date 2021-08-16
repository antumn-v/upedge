package com.upedge.common.web.advice;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

//@ControllerAdvice
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
//		过滤框架通信,beforebodywrite中对类型进行了判断可以满足此处的处理，因此本段代码删除
//		if(!returnType.getMethod().getDeclaringClass().getName().startsWith("com.lighting")) {
//			return false;
//		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
		//判断请求的来源，是否是 json请求，否则不处理
		if (selectedConverterType == MappingJackson2HttpMessageConverter.class
				&& (selectedContentType.equals(MediaType.APPLICATION_JSON)
						|| selectedContentType.equals(MediaType.APPLICATION_JSON_UTF8))) {
			if (body == null) {
				return new BaseResponse(ResultCode.NULL,"NULL");
			} else if (body instanceof BaseResponse) {
				// 已在handleexception注解的方法中做过处理
				return body;
			} else {
				// 未处理的异常
				if (returnType.getExecutable().getDeclaringClass().isAssignableFrom(BasicErrorController.class)) {
					BaseResponse res = new BaseResponse(ResultCode.EXCEPTION);
					HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
					if (req.getRequestURL().toString().contains("localhost")
							|| req.getRequestURL().toString().contains("127.0.0.1"))
						res.setData(body);
					return res;
				} else {
					return new BaseResponse(ResultCode.ERROR_EXCEPTION,body);
				}
			}
		}
		return body;
	}
	
	/**
	 * Validator 参数校验异常处理
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		return new ResponseEntity<Object>(new BaseResponse(ResultCode.ARGUMENTS_ERROR,ex.getMessage()),HttpStatus.OK);
	}

}
