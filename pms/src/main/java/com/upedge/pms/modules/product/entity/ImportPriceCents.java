package com.upedge.pms.modules.product.entity;

import lombok.Data;

/**
 * @author gx
 */
@Data
public class ImportPriceCents{

	/**
	 * 
	 */
private Integer id;
	/**
	 * 
	 */
private Long customerId;
	/**
	 * 
	 */
private Long userId;
	/**
	 * 
	 */
private Integer priceCents;
	/**
	 * 
	 */
private Integer comparePriceCents;
	/**
	 * 
	 */
private Integer priceRulesOn;
	/**
	 * 
	 */
private Integer comparePriceOn;

}
