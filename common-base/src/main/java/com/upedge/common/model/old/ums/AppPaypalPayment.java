package com.upedge.common.model.old.ums;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class AppPaypalPayment{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 收款方交易号
	 */
    private String saleId;
	/**
	 * 
	 */
    private String paymentId;
	/**
	 * 0=余额充值，1=普通订单，2=备库订单，3=批发订单
	 */
    private Integer orderType;
	/**
	 * 
	 */
    private String paymentMethod;
	/**
	 * 
	 */
    private String payerEmail;
	/**
	 * 
	 */
    private String payerName;
	/**
	 * 
	 */
    private String payerId;
	/**
	 * 
	 */
    private String state;
	/**
	 * 
	 */
    private String amount;
	/**
	 * 
	 */
    private String currency;
	/**
	 * 
	 */
    private String fixFee;
	/**
	 * 
	 */
    private String createTime;
	/**
	 * 
	 */
    private String updateTime;
	/**
	 * 
	 */
    private String payeeEmail;
	/**
	 * 
	 */
    private String merchantId;
	/**
	 * 0 : 订单支付  1：余额充值
	 */
    private Integer source;
	/**
	 * 0 : 待审核 1 ：正常
	 */
    private Integer type;
	/**
	 * 备注
	 */
    private String remark;
	/**
	 * 处理人
	 */
    private String adminUserId;
	/**
	 * 
	 */
    private Long appUserId;

}
