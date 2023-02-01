package com.upedge.ums.modules.affiliate.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DisableAffiliateRebateRequest {

    @NotNull
    private Long refereeId;

    @NotNull
    private Long referrerId;
}
