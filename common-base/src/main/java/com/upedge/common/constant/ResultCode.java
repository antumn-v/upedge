package com.upedge.common.constant;

public class ResultCode {

	/**
	 * 通用成功
	 */
	public static final int SUCCESS_CODE = 1;
	
	/**
	 * 通用失败
	 */
	public static final int FAIL_CODE = -1;

	public static final int FAIL_TOKEN_CODE=403;
	
	/**
	 * 无返回
	 */
	public static final int NULL = -4;
	
	/**
	 * 无需处理的异常
	 */
	public static final int EXCEPTION = -2;
	
	/**
	 * 必须处理的异常
	 */
	public static final int ERROR_EXCEPTION = -3;

	public static final int COLUMN_NAME_IS_KEYWORD = 1001;
	
	/**************************************
	 *           系统错误
	 *******************************************/
	
	/**
	 * 参数错误
	 */
	public static final int ARGUMENTS_ERROR = 100000;
	
	/**
	 * 没有主键
	 */
	public static final int NO_PRIMARY_KEY = 110000;
	
	
	/****************************************
	 *           用户错误
	 ***************************************/
		
	public static final int USER_NOT_EXIST = 200001;
	
	public static final int PASSWORD_WRONG = 200002;
	
	public static final int TOKEN_ERROR = 200003;
	
	public static final int CUSTOMER_NOT_EXIST = 200004;
	
	public static final int HAVE_NO_RIGHT = 200005;
	
	public static final int ORG_NO_EXIST = 210001;
	
	public static final int ORG_NOT_EMPTY = 210002;
	
	public static final int ROLE_NOT_EMPTY = 220001;
	
	public static final int MENU_HAVE_CHILDREN = 230001;
	
	public static final int MENU_PARENT_NOT_EXIST = 230002;
	
}
