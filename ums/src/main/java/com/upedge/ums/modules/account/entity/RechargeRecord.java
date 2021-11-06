package com.upedge.ums.modules.account.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class RechargeRecord{

	/**
	 *
	 */
	private Integer id;
	/**
	 *
	 */
	private Long rechargeId;
	/**
	 *
	 */
	private Long customerId;
	/**
	 *
	 */
	private Long orderId;
	/**
	 *
	 */
	private Integer orderType;
	/**
	 *
	 */
	private BigDecimal amount;
	/**
	 * 0=balance 1=rebate 2=credit
	 */
	private Integer source;
	/**
	 * 
	 */
    private Date createTime;

    private Integer seq;

	public RechargeRecord() {
	}

	public RechargeRecord(Long rechargeId, Long customerId, Long orderId, Integer orderType, BigDecimal amount, Integer source, Date date, Integer seq) {
		this.rechargeId = rechargeId;
		this.customerId = customerId;
		this.orderId = orderId;
		this.orderType = orderType;
		this.amount = amount;
		this.source = source;
		this.createTime = date;
		this.seq = seq;
	}
}
