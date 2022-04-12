package com.upedge.sms.modules.center.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
