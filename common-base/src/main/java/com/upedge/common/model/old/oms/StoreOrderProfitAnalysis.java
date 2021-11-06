package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class StoreOrderProfitAnalysis{

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
    private Long storeOrderId;
	/**
	 * 
	 */
    private BigDecimal storeOrderAmount;
	/**
	 * 
	 */
    private BigDecimal storeRefundAmount;
	/**
	 * 
	 */
    private BigDecimal upedgeRefundAmount;
	/**
	 * 
	 */
    private BigDecimal upedgeProductAmount;
	/**
	 * 潘达订单下每个店铺订单所对应的重量
	 */
    private BigDecimal upedgeOrderWeight;
	/**
	 * 
	 */
    private BigDecimal upedgeShippingPrice;
	/**
	 * 
	 */
    private String currencyCode;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
