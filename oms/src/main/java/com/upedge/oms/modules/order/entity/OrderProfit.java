package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderProfit{

	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private BigDecimal storeOrderItemAmount;
	/**
	 * 
	 */
    private BigDecimal storeOrderOtherFee;
	/**
	 * 
	 */
    private BigDecimal storeOrderRefundAmount;
	/**
	 * 
	 */
    private BigDecimal orderRefundAmount;
	/**
	 * 
	 */
    private BigDecimal orderPayAmount;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
