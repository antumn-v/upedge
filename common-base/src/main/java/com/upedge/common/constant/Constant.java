package com.upedge.common.constant;

import java.math.BigDecimal;

public class Constant {

	/**
	 * token在redis中的前缀
	 */
	public static final String TOKEN_REDIS_PREFIX = "L_T-";

	public static final String MANAGER_REDIS_PREFIX = "M_T-";

	/**
	 * token在header中的key
	 */
	public static final String TOKEN_HEADER_KEY = "X-Token";
	
	/**
	 * 密码加密key
	 */
	public static final String ENCRYPT_KEY = "159753";
	
	/**
	 * token过期时间
	 */
	public static final long TOKEN_EXPIRE_TIME = 3;
	
	/**
	 * 部门根节点编码
	 */
	public static final Long ROOT_ORGANIZATION = 0L;
	
	/**
	 * 菜单根节点编码
	 */
	public static final String ROOT_MENU = "0";
	
	/**
	 * 36进制
	 */
	public static final int SYSTEM_36 = 36;
	
	/**
	 * 部门编码字节数
	 */
	public static final int ORG_CODE_MAX_LENGTH = 3;
	
	/**
	 * 注册用户默认角色
	 */
	public static final String ORG_DEFAULT_ROLE = "default role";
	
	public static final String AVATAR = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
	
	public static final String STRING_UNION_CODE = "|";
	
	public static final String STRING_SPLIT_CODE = "\\|";
	
	public static final int YES = 1;
	
	public static final int NO = 0;

	public static final String MESSAGE_SUCCESS = " request success ";
	
	public static final String MESSAGE_FAIL = " request failed ";

	public static final String INIT_PASS="123456";

	public static final Long APP_APPLICATION_ID=1L;

	public static final Long ADMIN_APPLICATION_ID=2L;

	public static final BigDecimal PAYPAL_FEE_PERCENTAGE = new BigDecimal("0.044");

}
