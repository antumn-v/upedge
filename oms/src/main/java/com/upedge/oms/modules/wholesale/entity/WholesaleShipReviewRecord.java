package com.upedge.oms.modules.wholesale.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class WholesaleShipReviewRecord{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Long wholesaleOrderId;
	/**
	 * 
	 */
    private String shipMethodName;
	/**
	 * 
	 */
    private BigDecimal shipPrice;
	/**
	 * 0=支付前审核，1=支付后审核
	 */
    private Integer reviewType;
	/**
	 * 0=未通过 1=通过
	 */
    private Integer reviewStatus;
	/**
	 * 
	 */
    private Long reviewUserId;
	/**
	 * 
	 */
    private Date createTime;

}
