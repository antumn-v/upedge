package com.upedge.common.exception;

import com.upedge.common.base.BaseException;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.enums.CustomerExceptionEnum;

/**
 * 业务处理异常，在业务逻辑中出现错误可以抛出此异常
 * @author 荀立坤
 * @date 2020年6月20日
 */
public class CustomerException extends BaseException {

	private static final long serialVersionUID = 2180716028237460672L;

	public CustomerException(int code, String message) {
		super(code,message);
	}

	public CustomerException(CustomerExceptionEnum customerExceptionEnum) {
		super(customerExceptionEnum.getErrorCode(),customerExceptionEnum.getErrorMessage());
	}

	public CustomerException(String message) {
		super(ResultCode.FAIL_CODE,message);
	}


}
