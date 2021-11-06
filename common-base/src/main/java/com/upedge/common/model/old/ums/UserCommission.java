package com.upedge.common.model.old.ums;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xwCui
 */
@Data
public class UserCommission{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private BigDecimal commission;
	/**
	 * 
	 */
    private Integer emailPrompt;
	/**
	 * 
	 */
    private Double factorA;
	/**
	 * 
	 */
    private Double factorB;
	/**
	 * 
	 */
    private Double factorC;
	/**
	 * 
	 */
    private Integer stockDays;
	/**
	 * 
	 */
    private Integer inventoryNotice;

}
