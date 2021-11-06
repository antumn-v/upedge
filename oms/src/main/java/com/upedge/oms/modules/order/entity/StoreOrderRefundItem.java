package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class StoreOrderRefundItem{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long storeOrderRefundId;
	/**
	 * 
	 */
    private Long storeOrderItemId;

    private String platOrderItemId;
	/**
	 * 
	 */
    private Integer refundQuantity;
	/**
	 * 
	 */
    private BigDecimal refundAmount;

}
