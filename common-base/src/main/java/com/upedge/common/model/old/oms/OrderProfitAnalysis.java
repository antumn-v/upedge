package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class OrderProfitAnalysis{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long appOrderId;
	/**
	 * 
	 */
    private Long storeOrderId;
	/**
	 * 产品费+运费+paypal+手续费
	 */
    private BigDecimal upedgeOrderAmount;
	/**
	 * 
	 */
    private BigDecimal storeOrderAmount;
	/**
	 * 
	 */
    private BigDecimal upedgeRefundAmount;
	/**
	 * 
	 */
    private BigDecimal storeRefundAmount;
	/**
	 * 
	 */
    private String currencyCode;
	/**
	 * 
	 */
    private BigDecimal currencyRate;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
