package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class StoreOrderRefund{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long storeId;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private Long refundId;
	/**
	 * 
	 */
    private Long storeOrderId;
	/**
	 * 
	 */
    private BigDecimal refundAmount;
	/**
	 * 
	 */
    private BigDecimal usdAmount;
	/**
	 * 
	 */
    private BigDecimal otherFee;
	/**
	 * 
	 */
    private BigDecimal usdRate;
	/**
	 * 
	 */
    private BigDecimal currencyRate;
	/**
	 * 
	 */
    private String note;
	/**
	 * 0=退产品费，1=只退运费不退产品费
	 */
    private Integer refundType;
	/**
	 * 
	 */
    private Date createdAt;
	/**
	 * 
	 */
    private Date createTime;

}
