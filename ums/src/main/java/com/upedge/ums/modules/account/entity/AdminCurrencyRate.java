package com.upedge.ums.modules.account.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AdminCurrencyRate{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String currencyCode;
	/**
	 * 
	 */
    private String currencyName;
	/**
	 * 货币对人民币的汇率
	 */
    private BigDecimal cnyRate;
	/**
	 * 货币对美元的汇率
	 */
    private BigDecimal usdRate;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private String adminUser;
	/**
	 * 潘达账号
	 */
    private Long accountId;

}
