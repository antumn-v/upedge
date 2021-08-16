package com.upedge.ums.modules.account.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class PaypalPayment{

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
	 * 备注
	 */
    private String remark;
	/**
	 * 
	 */
    private Integer accountPaymethodId;
	/**
	 * 
	 */
    private Long accountId;
	/**
	 * 
	 */
    private Integer requestId;
	/**
	 * 0=充值 1=备库订单 2=普通订单 3=批发订单
	 */
    private Integer orderType;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Long userId;

}
