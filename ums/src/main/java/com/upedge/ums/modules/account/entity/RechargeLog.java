package com.upedge.ums.modules.account.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class RechargeLog{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long accountId;
	/**
	 * 0 <=recharge_type < 3 表示充值申请ID，>3表示退款ID
	 */
    private Long relateId;
	/**
	 * 
	 */
    private BigDecimal amount;
	/**
	 * 
	 */
    private BigDecimal rebate;
	/**
	 * 
	 */
    private BigDecimal payed;
	/**
	 * 0 未使用 1 已使用 2已完成 3已撤销
	 */
    private Integer rechargeStatus;
	/**
	 * 0:电汇 1:paypal充值 2:payoneer充值，3=返点充值 4.退款充值，5,内部转账
	 */
    private Integer rechargeType;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
