package com.upedge.sms.modules.overseaWarehouse.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gx
 */
@Data
public class OverseaWarehouseServiceOrderFreight{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Integer shipType;
	/**
	 * 
	 */
    private BigDecimal shipPrice;

}
