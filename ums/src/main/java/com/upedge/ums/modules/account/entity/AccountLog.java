package com.upedge.ums.modules.account.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class AccountLog{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long accountId;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 支付/扣款 = 0，退款/收款 = 1，还款 = 2
	 */
    private Integer transactionType;
	/**
	 * 充值 = 0,备库 = 1，普通 = 2，批发 = 3，转账 = 4，提现 = 5
	 */
    private Integer orderType;
	/**
	 * 账户 = 0，paypal = 1，payoneer = 2，佣金 = 3
	 */
    private Integer payMethod;
	/**
	 * 
	 */
    private Long transactionId;
	/**
	 * 
	 */
    private BigDecimal balance;
	/**
	 * 
	 */
    private BigDecimal rebate;
	/**
	 * 
	 */
    private BigDecimal credit;
	/**
	 * 手续费
	 */
    private BigDecimal fixFee;
	/**
	 * 
	 */
    private Date createTime;

}
