package com.upedge.common.base;

import com.upedge.common.constant.ResultCode;

public class BaseException extends Exception {

	private static final long serialVersionUID = -5746383613543896726L;

	private int errorCode;
	
	private String message;
	
	public BaseException() {
		
	}
	
	public BaseException(Exception e) {
		this.errorCode =  ResultCode.FAIL_CODE;
		this.message = e.getMessage();
	}

	public BaseException(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
