package com.upedge.ums.modules.affiliate.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
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
	/**
	 * 推荐人ID
	 */
    private Long referrerId;
	/**
	 * 交易ID
	 */
    private Long orderId;
	/**
	 * 充值 = 0,备库 = 1，普通 = 2，批发 = 3，转账 = 4，提现 = 5
	 */
    private Integer orderType;
	/**
	 * 每笔订单的佣金
	 */
    private BigDecimal commission;
	/**
	 * 退款=0，支付=1 提现=2
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

}
