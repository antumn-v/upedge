package com.upedge.common.model.old.ums;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AccountFlow{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 1:转账充值 2:余额支付 3paypal支付订单 4订单退款 5余额还款,6paypal充值，7paypal还款  8内部转账收款 9内部转账扣款 10:余额支付备库订单  11paypal支付备库订单，12:账外提现，13佣金提现 14备库单退款 15 备库单还款信用额度 16payoneer充值 17payoneer还款 18返点奖励，19余额支付批发订单，20 paypal支付批发订单，21批发订单退款，22批发订单退款还款信用额度 23账外提现取消 24内部转账收款还信用额度，25：payoneer api充值，26：payoneer api充值还款
	 */
    private Integer type;
	/**
	 * 
	 */
    private String name;
	/**
	 * 
	 */
    private BigDecimal amount;
	/**
	 * 
	 */
    private BigDecimal benefits;
	/**
	 * 
	 */
    private BigDecimal credit;
	/**
	 * 
	 */
    private Long relateId;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Long appUserId;
	/**
	 * 
	 */
    private BigDecimal fixFee;

}
