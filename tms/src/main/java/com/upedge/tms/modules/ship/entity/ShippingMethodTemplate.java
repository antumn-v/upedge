package com.upedge.tms.modules.ship.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class ShippingMethodTemplate{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 可以运输的模板id
	 */
    private Long shippingId;
	/**
	 * 运输方式id
	 */
    private Long methodId;
	/**
	 * 
	 */
    private Integer sort;

}
