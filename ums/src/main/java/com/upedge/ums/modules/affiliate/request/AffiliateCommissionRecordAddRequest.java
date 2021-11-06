package com.upedge.ums.modules.affiliate.request;

import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AffiliateCommissionRecordAddRequest{

    /**
    * 被推荐人ID
    */
    private Long refereeId;
    /**
    * 交易ID
    */
    private Long orderId;
    /**
    * 0=普通订单，1=批发订单
    */
    private Integer orderType;
    /**
    * 每笔订单的佣金
    */
    private BigDecimal orderAmount;


    public AffiliateCommissionRecord toAffiliateCommissionRecord(){
        AffiliateCommissionRecord affiliateCommissionRecord=new AffiliateCommissionRecord();
        affiliateCommissionRecord.setRefereeId(refereeId);
        affiliateCommissionRecord.setOrderId(orderId);
        affiliateCommissionRecord.setOrderType(orderType);
        affiliateCommissionRecord.setCommission(orderAmount.multiply(new BigDecimal("0.01")));
        affiliateCommissionRecord.setState(0);
        affiliateCommissionRecord.setCreateTime(new Date());
        return affiliateCommissionRecord;
    }

}
