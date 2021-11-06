package com.upedge.ums.modules.affiliate.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AffiliateWithdrawalRejectRequest {

    @NotNull
    Long withdrawalId;

    String rejectReason;
}
