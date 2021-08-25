package com.upedge.ums.modules.account.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class RechargeRequestLog{
	//银行转账
	public static final Integer TRANSFER_REQUEST_TYPE = 0;
	//PayPal
	public static final Integer PAYPAL_REQUEST_TYPE = 0;

	public static final Integer PENDING = 0;
	public static final Integer SUCCESS = 1;
	public static final Integer FAILED = 0;

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
    private Integer accountPaymethodId;
	/**
	 * 提交充值申请的用户
	 */
    private Long customerId;
	/**
	 * 申请用户ID
	 */
    private Long requestUserId;
	/**
	 * 处理人
	 */
    private Long handleUserId;
	/**
	 * 还款金额
	 */
    private BigDecimal repaymentAmount;
	/**
	 * 到账金额
	 */
    private BigDecimal amount;
	/**
	 * 
	 */
    private BigDecimal benefits;
	/**
	 * 申请金额
	 */
    private BigDecimal customerMoney;
	/**
	 * 备注
	 */
    private String remarks;
	/**
	 * 流水号
	 */
    private String transferFlow;
	/**
	 * 凭证
	 */
	private String voucher;
	/**
	 * 0:电汇 1:paypal充值 2:payoneer充值,3:payoneer申请
	 */
    private Integer rechargeType;
	/**
	 * 0=审核中，1=成功，2=失败  3=payoneer待提交付款请求
	 */
    private Integer status;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
