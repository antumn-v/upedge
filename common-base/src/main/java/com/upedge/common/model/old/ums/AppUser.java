package com.upedge.common.model.old.ums;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppUser{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 用户名
	 */
    private String username;
	/**
	 * 密码
	 */
    private String password;
	/**
	 * 用户真实姓名
	 */
    private String name;
	/**
	 * 邮箱（作为登陆账号）
	 */
    private String email;
	/**
	 * 用户联系方式
	 */
    private String phone;
	/**
	 * 盐空间
	 */
    private String salt;
	/**
	 * 用户状态
	 */
    private String state;
	/**
	 * 注册时间
	 */
    private Date createTime;
	/**
	 * 余额
	 */
    private BigDecimal balance;
	/**
	 * 返点
	 */
    private BigDecimal benefitsMoney;
	/**
	 * 
	 */
    private BigDecimal credit;
	/**
	 * 
	 */
    private BigDecimal creditLimit;
	/**
	 * 
	 */
    private Integer priceCents;
	/**
	 * 
	 */
    private Integer comparePriceCents;
	/**
	 * 提交速卖通订单时覆盖用户联系方式
	 */
    private String aliOrderPhone;
	/**
	 * 提交速卖通订单时的统一备注
	 */
    private String aliOrderNote;
	/**
	 * 
	 */
    private Integer priceRulesOn;
	/**
	 * 
	 */
    private Integer comparePriceOn;
	/**
	 * 
	 */
    private String verificationCode;
	/**
	 * 
	 */
    private Long verificationCodeTime;
	/**
	 * 登录次数
	 */
    private Integer loginCount;
	/**
	 * 最近登录时间
	 */
    private Date lastLoginTime;
	/**
	 * 状态1:启用0:禁用
	 */
    private Integer loginState;
	/**
	 * 注册来源，UPEDGE或Shopify
	 */
    private String source;
	/**
	 * 
	 */
    private Integer orderQuantityAccess;
	/**
	 * admin用户信息完善状态，对应app_user_info 0:未完善 1:部分完善 2:已完善
	 */
    private Integer userInfoState;

    private String pwd;

}
