package com.upedge.common.model.old.ums;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppWithdrawal{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 申请人
	 */
    private Long userId;
	/**
	 * 收款账户
	 */
    private String account;
	/**
	 * 提现金额
	 */
    private BigDecimal amount;
	/**
	 * PayPal,Payoneer,SourcinBox
	 */
    private String path;
	/**
	 * 备注
	 */
    private String remark;
	/**
	 * 申请中=0；申请通过=1，拒绝申请=2
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
	/**
	 * 拒绝原因
	 */
    private String reason;
	/**
	 * 
	 */
    private String adminUserId;
	/**
	 * 潘达转账账号
	 */
    private String accountId;

}
