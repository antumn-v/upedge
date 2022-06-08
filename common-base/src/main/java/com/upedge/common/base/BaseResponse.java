package com.upedge.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;

import java.io.Serializable;
import java.util.Collection;

public class BaseResponse implements Serializable {

	private static final long serialVersionUID = -4164042493503163521L;

	protected int code = 0;
	
	protected String msg;
	
	protected Object data;
	
	protected Object req;


	public BaseResponse() {
		
	}
	public BaseResponse(int code) {
		this.code = code;
	}
	public BaseResponse(String msg) {
		this.msg = msg;
	}
	
	public BaseResponse(BaseException e) {
		this.code = e.getErrorCode();
		this.msg = e.getMessage();
	}
	
	public BaseResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public BaseResponse(int code, Object data) {
		this.code = code;
		this.data = data;
	}
	
	public BaseResponse(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public BaseResponse(int code, String msg, Object data, Object req) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.req = req;
	}

	public static BaseResponse success(){
		return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
	}

	public static BaseResponse success(Object data , Object req){
		return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,data,req);
	}
	public static BaseResponse success(Object data){
		return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,data);
	}

	public static BaseResponse failed(){
		return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
	}

	public static BaseResponse failed(String message){
		return new BaseResponse(ResultCode.FAIL_CODE, message);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "BaseResponse{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				", req=" + req +
				'}';
	}

	public Object getReq() {
		return req;
	}
	public void setReq(Object req) {
		this.req = req;
	}
}
