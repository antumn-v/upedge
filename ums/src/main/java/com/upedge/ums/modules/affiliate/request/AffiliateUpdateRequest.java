package com.upedge.ums.modules.affiliate.request;

import com.upedge.ums.modules.affiliate.entity.Affiliate;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AffiliateUpdateRequest{

    /**
     * 推荐人ID
     */
    private Long referrerId;
    /**
     * 被推荐人ID
     */
    private Long refereeId;
    /**
     * 被推荐人的提成
     */
    private BigDecimal refereeCommission;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 来源 0:app 1:admin
     */
    private Integer source;

    public Affiliate toAffiliate(Long id){
        Affiliate affiliate=new Affiliate();
        affiliate.setId(id);
        affiliate.setReferrerId(referrerId);
        affiliate.setRefereeId(refereeId);
        affiliate.setRefereeCommission(refereeCommission);
        affiliate.setCreateTime(createTime);
        affiliate.setUpdateTime(updateTime);
        affiliate.setSource(source);
        return affiliate;
    }

}
