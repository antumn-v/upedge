package com.upedge.ums.modules.user.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class CustomerVipRebateRecord{

	public static Integer RECEIVE_REBATE = 1;
	public static Integer ORDER_PAY = 0;
	public static Integer ORDER_REFUND = 2;

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
	 * 1=增加  0=支付订单，2=订单退款
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
