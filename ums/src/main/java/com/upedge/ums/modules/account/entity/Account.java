package com.upedge.ums.modules.account.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class Account{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 名称
	 */
    private String name;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 余额
	 */
    private BigDecimal balance;
	/**
	 * 返利
	 */
    private BigDecimal rebate;
	/**
	 * 已使用的信用额度
	 */
    private BigDecimal credit;
	/**
	 * 可使用的信用额度
	 */
    private BigDecimal creditLimit;
	/**
	 * 佣金
	 */
    private BigDecimal commission;
	/**
	 * 0=禁用，1=正常
	 */
    private Integer status;
	/**
	 * 是否为默认账户
	 */
    private Boolean isDefault;

}
