package com.upedge.sms.modules.wholesale.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class WholesaleOrder{

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
    private BigDecimal payAmount;
	/**
	 * 
	 */
    private Integer shipType;
	/**
	 * 
	 */
    private BigDecimal shipPrice;
	/**
	 * 
	 */
    private BigDecimal totalWeight;
	/**
	 * 
	 */
    private BigDecimal volumeWeight;

	/**
	 * 
	 */
    private BigDecimal productAmount;
	/**
	 * 
	 */
    private BigDecimal productDischargeAmount;
	/**
	 * 0=balance,1=paypal
	 */
    private Integer payMethod;
	/**
	 * 
	 */
    private Date payTime;
	/**
	 * 
	 */
    private Long paymentId;
	/**
	 * 支付状态,待支付=0，已支付=1，取消订单=-1，支付中=2
	 */
    private Integer payState;
	/**
	 * 退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
	 */
    private Integer refundState;
	/**
	 * 0=未发货。1=已发货。
	 */
    private Integer shipState;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 运输单号
	 */
    private String trackingCode;

	public void init(){
		this.payState = 0;
		this.refundState = 0;
		this.shipState = 0;
		this.createTime = new Date();
		this.updateTime = createTime;
	}

}
