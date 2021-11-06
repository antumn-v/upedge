package com.upedge.oms.modules.wholesale.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class WholesaleReshipInfo{

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
	/**
	 * 补发次数
	 */
    private Integer reshipTimes;

}
