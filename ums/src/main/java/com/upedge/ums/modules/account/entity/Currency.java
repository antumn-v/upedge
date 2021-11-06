package com.upedge.ums.modules.account.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class Currency{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private String currencyCode;
	/**
	 * se_buy / 100
	 */
    private BigDecimal cnyRate;

    private BigDecimal usdRate;
	/**
	 * BOC:中国银行
	 */
    private String bankno;
	/**
	 * 现汇卖出价
	 */
    private BigDecimal seSell;
	/**
	 * 现汇买入价
	 */
    private BigDecimal seBuy;
	/**
	 * 现钞卖出价
	 */
    private BigDecimal cnSell;
	/**
	 * 现钞买入价
	 */
    private BigDecimal cnBuy;
	/**
	 * 中间价
	 */
    private BigDecimal middle;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * API数据更新时间
	 */
    private Date apiUpdateTime;
	/**
	 * app更新时间，每天零点更新
	 */
    private Date appUpdateTime;

	private Date upddate;

}
