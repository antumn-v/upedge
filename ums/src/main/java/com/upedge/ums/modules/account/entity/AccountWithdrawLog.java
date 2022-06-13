package com.upedge.ums.modules.account.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class AccountWithdrawLog{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long accountId;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private BigDecimal balance;
	/**
	 * 
	 */
    private BigDecimal affiliateRebate;
	/**
	 * 
	 */
    private BigDecimal vipRebate;
	/**
	 * 收款账号
	 */
    private String collectAccount;
	/**
	 * 收款方式:0=paypal
	 */
    private Integer collectType;
	/**
	 * 提现前记录
	 */
    private String logBeforeWithdraw;

	private Integer status;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
