package com.upedge.common.model.old.ums;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppRecharge{

	/**
	 * 
	 */
    private String rechargeId;
	/**
	 * 
	 */
    private Long appUserId;
	/**
	 * 
	 */
    private String adminUserId;
	/**
	 * 
	 */
    private String rechargeImage;
	/**
	 * 到账金额
	 */
    private BigDecimal rechargeMoney;
	/**
	 * 
	 */
    private BigDecimal benefitsMoney;
	/**
	 * 申请金额
	 */
    private BigDecimal customerMoney;
	/**
	 * 收款账号
	 */
    private String receivingAccount;
	/**
	 * 银行流水号
	 */
    private String transferFlow;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 0:未认证，1：认证成功，-1认证失败
	 */
    private Integer state;
	/**
	 * 留言
	 */
    private String message;
	/**
	 * 
	 */
    private String currency;
	/**
	 * 认证失败原因
	 */
    private String remarks;
	/**
	 * 相对路径
	 */
    private String absolutePath;
	/**
	 * 0:电汇 1:paypal充值 2:payoneer申请,3:payoneer充值
	 */
    private Integer rechargeType;
	/**
	 * 
	 */
    private String payUrl;

}
