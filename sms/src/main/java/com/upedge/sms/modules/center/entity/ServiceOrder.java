package com.upedge.sms.modules.center.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class ServiceOrder{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Integer serviceType;

	private Long relateId;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Integer serviceState;
	/**
	 * 
	 */
    private Integer payState;

	private Integer refundState;
	/**
	 * 
	 */
    private BigDecimal payAmount;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Date finishTime;
	/**
	 * 
	 */
    private Long managerId;

}
