package com.upedge.ums.modules.manager.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class ManagerCommissionRecord{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long managerId;

	private Long customerId;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private BigDecimal commission;
	/**
	 * 
	 */
    private Date createTime;

}
