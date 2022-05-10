package com.upedge.ums.modules.user.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class CustomerVipRebateRecord{

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
    private Long accountId;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private BigDecimal rebate;
	/**
	 * 1=增加  0=减少
	 */
    private Integer rebateType;
	/**
	 * 
	 */
    private Date createTime;

	public CustomerVipRebateRecord() {
	}

	public CustomerVipRebateRecord(Long customerId, Long accountId, Long orderId, BigDecimal rebate, Integer rebateType, Date createTime) {
		this.customerId = customerId;
		this.accountId = accountId;
		this.orderId = orderId;
		this.rebate = rebate;
		this.rebateType = rebateType;
		this.createTime = createTime;
	}
}
