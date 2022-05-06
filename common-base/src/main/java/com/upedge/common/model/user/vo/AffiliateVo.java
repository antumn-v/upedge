package com.upedge.common.model.user.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AffiliateVo {

    private Long referrerId;
    /**
     * 被推荐人ID
     */
    private Long refereeId;
    /**
     * 被推荐人的提成
     */
    private BigDecimal refereeCommission;
}
