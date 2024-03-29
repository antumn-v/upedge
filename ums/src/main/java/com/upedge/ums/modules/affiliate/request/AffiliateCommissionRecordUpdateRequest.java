package com.upedge.ums.modules.affiliate.request;

import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AffiliateCommissionRecordUpdateRequest{

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
    private BigDecimal commission;
    /**
     * 退款=0，支付=1 现在提现=2 余额提现=3 批发退款=4
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

    public AffiliateCommissionRecord toAffiliateCommissionRecord(Long id){
        AffiliateCommissionRecord affiliateCommissionRecord=new AffiliateCommissionRecord();
        affiliateCommissionRecord.setId(id);
        affiliateCommissionRecord.setRefereeId(refereeId);
        affiliateCommissionRecord.setOrderId(orderId);
        affiliateCommissionRecord.setOrderType(orderType);
        affiliateCommissionRecord.setCommission(commission);
        affiliateCommissionRecord.setState(state);
        affiliateCommissionRecord.setCreateTime(createTime);
        affiliateCommissionRecord.setUpdateTime(updateTime);
        return affiliateCommissionRecord;
    }

}
