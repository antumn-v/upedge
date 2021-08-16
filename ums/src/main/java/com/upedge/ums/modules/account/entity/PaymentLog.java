package com.upedge.ums.modules.account.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
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
	 * 1=备库订单 2=普通订单 3=批发订单
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
