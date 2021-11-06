package com.upedge.oms.modules.rules.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class OrderShipRuleCountry{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
	private Long customerId;

    private Long shipRuleId;
	/**
	 * 
	 */
    private String countryName;
	/**
	 * 
	 */
    private Long areaId;

}
