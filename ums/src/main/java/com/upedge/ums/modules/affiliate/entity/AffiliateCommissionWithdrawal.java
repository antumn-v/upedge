package com.upedge.ums.modules.affiliate.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class AffiliateCommissionWithdrawal{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 申请人
	 */
    private Long customerId;
	/**
	 * 申请提现账户
	 */
    private Long withdrawalAccountId;
	/**
	 * 提现金额
	 */
    private BigDecimal amount;
	/**
	 * 0=SourcinBox,1=PayPal,2=Payoneer
	 */
    private Integer path;
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
	 * 收款账户
	 */
    private String receiveAccount;
	/**
	 * 后台付款账号
	 */
    private String paymentAccount;
	/**
	 * 
	 */
    private String managerCode;

}
