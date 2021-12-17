package com.upedge.ums.modules.affiliate.request;

import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionWithdrawal;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AffiliateCommissionWithdrawalAddRequest{


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
    * 收款账户
    */
    private String receiveAccount;


    public AffiliateCommissionWithdrawal toAffiliateCommissionWithdrawal(){
        AffiliateCommissionWithdrawal affiliateCommissionWithdrawal=new AffiliateCommissionWithdrawal();
        affiliateCommissionWithdrawal.setAmount(amount);
        affiliateCommissionWithdrawal.setPath(path);
        affiliateCommissionWithdrawal.setRemark(remark);
        affiliateCommissionWithdrawal.setState(0);
        affiliateCommissionWithdrawal.setCreateTime(new Date());
        affiliateCommissionWithdrawal.setReceiveAccount(receiveAccount);
        return affiliateCommissionWithdrawal;
    }

}
