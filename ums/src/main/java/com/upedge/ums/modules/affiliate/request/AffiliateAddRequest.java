package com.upedge.ums.modules.affiliate.request;

import com.upedge.ums.modules.affiliate.entity.Affiliate;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AffiliateAddRequest{

    /**
    * 推荐人ID
    */
    @NotNull
    private Long referrerId;
    /**
    * 被推荐人ID
    */
    @NotNull
    private Long refereeId;

    Integer source;

    BigDecimal refereeCommission;

    public Affiliate toAffiliate(){
        Affiliate affiliate=new Affiliate();
        affiliate.setReferrerId(referrerId);
        affiliate.setRefereeId(refereeId);
        affiliate.setRefereeCommission(refereeCommission);
        affiliate.setCommissionAmount(BigDecimal.ZERO);
        affiliate.setCreateTime(new Date());
        affiliate.setUpdateTime(new Date());
        affiliate.setSource(source);
        affiliate.setRebateState(false);
        return affiliate;
    }

}
