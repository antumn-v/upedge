package com.upedge.oms.modules.stock.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class StockAdviceSetting{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 5
	 */
    private BigDecimal factorA = new BigDecimal("0.3").setScale(2);
	/**
	 * 10
	 */
    private BigDecimal factorB = new BigDecimal("0.5").setScale(2);
	/**
	 * 15
	 */
    private BigDecimal factorC = new BigDecimal("0.2").setScale(2);
	/**
	 * 
	 */
    private Integer stockDays = 7;
	/**
	 * 
	 */
    private Integer inventoryNotice = 0;

}
