package com.upedge.ums.modules.affiliate.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AffiliateCommissionRecord{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 被推荐人ID
	 */
    private Long refereeId;


    private Long referrerId;
	/**
	 * 交易ID
	 */
    private Long orderId;
	/**
	 * 0=普通订单，1=批发订单
	 */
    private Integer orderType;
	/**
	 * 每笔订单的佣金
	 */
    private BigDecimal commission;
	/**
	 * 退款=0，支付=1 现在提现=2 余额提现=3 批发退款=4
	 */
    private Integer state;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;


	public AffiliateCommissionRecord(Long refereeId, Long referrerId, Long orderId, Integer orderType, BigDecimal commission, Integer state, Date createTime, Date updateTime) {
		this.refereeId = refereeId;
		this.referrerId = referrerId;
		this.orderId = orderId;
		this.orderType = orderType;
		this.commission = commission;
		this.state = state;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public AffiliateCommissionRecord() {
	}
}
