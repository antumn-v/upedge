package com.upedge.ums.modules.affiliate.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class AffiliateCommissionWithdrawalAddRequest{

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

    public AffiliateCommissionWithdrawal toAffiliateCommissionWithdrawal(){
        AffiliateCommissionWithdrawal affiliateCommissionWithdrawal=new AffiliateCommissionWithdrawal();
        affiliateCommissionWithdrawal.setCustomerId(customerId);
        affiliateCommissionWithdrawal.setWithdrawalAccountId(withdrawalAccountId);
        affiliateCommissionWithdrawal.setAmount(amount);
        affiliateCommissionWithdrawal.setPath(path);
        affiliateCommissionWithdrawal.setRemark(remark);
        affiliateCommissionWithdrawal.setState(state);
        affiliateCommissionWithdrawal.setCreateTime(createTime);
        affiliateCommissionWithdrawal.setUpdateTime(updateTime);
        affiliateCommissionWithdrawal.setReason(reason);
        affiliateCommissionWithdrawal.setAdminUserId(adminUserId);
        affiliateCommissionWithdrawal.setReceiveAccount(receiveAccount);
        affiliateCommissionWithdrawal.setPaymentAccount(paymentAccount);
        affiliateCommissionWithdrawal.setManagerCode(managerCode);
        return affiliateCommissionWithdrawal;
    }

}
