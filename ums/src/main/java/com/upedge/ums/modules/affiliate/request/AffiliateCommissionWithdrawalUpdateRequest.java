package com.upedge.ums.modules.affiliate.request;

import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AffiliateCommissionWithdrawalUpdateRequest{

    /**
     * 申请人
     */
    private Long customerId;
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

    public AffiliateCommissionWithdrawal toAffiliateCommissionWithdrawal(Long id){
        AffiliateCommissionWithdrawal affiliateCommissionWithdrawal=new AffiliateCommissionWithdrawal();
        affiliateCommissionWithdrawal.setId(id);
        affiliateCommissionWithdrawal.setCustomerId(customerId);
        affiliateCommissionWithdrawal.setAmount(amount);
        affiliateCommissionWithdrawal.setPath(path);
        affiliateCommissionWithdrawal.setRemark(remark);
        affiliateCommissionWithdrawal.setState(state);
        affiliateCommissionWithdrawal.setCreateTime(createTime);
        affiliateCommissionWithdrawal.setUpdateTime(updateTime);
        affiliateCommissionWithdrawal.setReason(reason);
        affiliateCommissionWithdrawal.setManagerCode(managerCode);
        affiliateCommissionWithdrawal.setReceiveAccount(receiveAccount);
        affiliateCommissionWithdrawal.setPaymentAccount(paymentAccount);
        return affiliateCommissionWithdrawal;
    }

}
