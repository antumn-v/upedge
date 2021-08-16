package com.upedge.common.constant;

public class BaseCode {

	/**
	 * 客户状态-正常
	 */
	public static final int CUSTOMER_ON = 0;
	
	/**
	 * 客户状态 - 待审核
	 */
	public static final int CUSTOMER_WAITING = 1;
	
	/**
	 * 客户状态 - 已拒绝
	 */
	public static final int CUSTOMER_REJECT = 2;
	
	/**
	 * 客户状态-停用
	 */
	public static final int CUSTOMER_OFF = 3;
	
	/**
	 * 用户状态-正常
	 */
	public static final int USER_ON = 1;
	
	/**
	 * 用户状态-停用
	 */
	public static final int USER_OFF = 0;
	
	/**
	 * 用户状态-未修改密码
	 */
	public static final int USER_FIRST = 2;
	
	/**
	 * 用户类型-超级管理员-用于User中的uer_type属性
	 */
	public static final int USER_ROLE_SUPERADMIN = 0;
	
	/**
	 * 用户类型-管理员-用于User中的uer_type属性
	 */
	public static final int USER_ROLE_ADMIN = 1;
	
	/**
	 * 用户类型-普通用户-用于User中的uer_type属性
	 */
	public static final int USER_ROLE_NORMAL = 2;

	public static final String INIT_LOGIN_PASS="123456";

	/**
	 * 证件类型-身份证
	 */
	public static final int ID_TYPE_SFZ = 1;

	/**
	 * 菜单类型 3:超级管理员 所有
	 */
	public static final int MENU_SUPERADMIN=3;

	/**
	 * 菜单类型 2:管理员-超级管理员
	 */
	public static final int MENU_ADMIN=2;

	/**
	 * 菜单类型 1:普通用户
	 */
	public static final int MENU_NORMAL=1;
}
