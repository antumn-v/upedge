package com.upedge.oms.modules.order.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class OrderReshipInfo{

	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Long originalOrderId;
	/**
	 * 
	 */
    private String reason;

    private Integer reshipTimes;
}