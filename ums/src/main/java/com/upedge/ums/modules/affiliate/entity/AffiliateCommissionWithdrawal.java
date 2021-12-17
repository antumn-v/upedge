package com.upedge.ums.modules.affiliate.entity;

import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.affiliate.request.AffiliateWithdrawalRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
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
	 * PayPal,Payoneer,UPEDGE
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
    private String managerCode;
	/**
	 * 收款账户
	 */
    private String receiveAccount;
	/**
	 * 付款账号
	 */
    private String paymentAccount;

	private Integer gteState;

	public AffiliateCommissionWithdrawal() {
	}

	public AffiliateCommissionWithdrawal getAffiliateCommissionWithdrawal(Session session, AffiliateWithdrawalRequest request){
		AffiliateCommissionWithdrawal withdrawal = new AffiliateCommissionWithdrawal();
		withdrawal.setReceiveAccount(request.getAccount());
		withdrawal.setAmount(request.getMoney());
		withdrawal.setPath(request.getType());
		withdrawal.setRemark(request.getRemark());
		withdrawal.setCustomerId(session.getCustomerId());
		withdrawal.setWithdrawalAccountId(session.getAccountId());
		withdrawal.setCreateTime(new Date());
		withdrawal.setUpdateTime(new Date());
		withdrawal.setState(0);
		return withdrawal;
	}

}
