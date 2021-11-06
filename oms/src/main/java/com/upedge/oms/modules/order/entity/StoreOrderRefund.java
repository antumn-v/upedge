package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author author
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
    private String platOrderRefundId;
	/**
	 * 
	 */
    private String platOrderId;
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

    private BigDecimal cnyRate;
	/**
	 * 
	 */
    private Date createTime;

    List<StoreOrderRefundItem> refundItems;

}
