package com.upedge.ums.modules.manager.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gx
 */
@Data
public class ManagerMonthCommission{
	/**
	 * 
	 */
    private Long managerId;
	/**
	 * 
	 */
    private String dateMonth;
	/**
	 * 
	 */
    private BigDecimal totalCommission;

}
