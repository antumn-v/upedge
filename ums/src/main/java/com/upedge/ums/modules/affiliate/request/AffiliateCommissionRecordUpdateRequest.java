package com.upedge.ums.modules.affiliate.request;

import com.upedge.ums.modules.affiliate.entity.AffiliateCommissionRecord;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class AffiliateCommissionRecordUpdateRequest{

    /**
     * 被推荐人ID
     */
    private Long refereeId;
    /**
     * 推荐人ID
     */
    private Long referrerId;
    /**
     * 交易ID
     */
    private Long orderId;
    /**
     * 充值 = 0,备库 = 1，普通 = 2，批发 = 3，转账 = 4，提现 = 5
     */
    private Integer orderType;
    /**
     * 每笔订单的佣金
     */
    private BigDecimal commission;
    /**
     * 退款=0，支付=1 提现=2
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
        affiliateCommissionRecord.setReferrerId(referrerId);
        affiliateCommissionRecord.setOrderId(orderId);
        affiliateCommissionRecord.setOrderType(orderType);
        affiliateCommissionRecord.setCommission(commission);
        affiliateCommissionRecord.setState(state);
        affiliateCommissionRecord.setCreateTime(createTime);
        affiliateCommissionRecord.setUpdateTime(updateTime);
        return affiliateCommissionRecord;
    }

}
