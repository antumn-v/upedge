package com.upedge.ums.modules.account.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class PaymentLog{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long accountId;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 0=备库订单 1=普通订单 2=批发订单
	 */
    private Integer orderType;
	/**
	 * 
	 */
    private BigDecimal amount;
	/**
	 * 
	 */
    private BigDecimal credit;
	/**
	 * 
	 */
    private BigDecimal rebate;
	/**
	 * 手续费
	 */
    private BigDecimal fee;
	/**
	 * 0 待确认 1已支付 2已撤销
	 */
    private Integer payStatus;
	/**
	 * 支付方式：0=recharge 1=paypal
	 */
    private Integer payType;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
