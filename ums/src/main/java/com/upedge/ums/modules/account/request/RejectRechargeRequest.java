package com.upedge.ums.modules.account.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RejectRechargeRequest {
    @NotNull
    private Long id;
    @NotNull
    private String rejectReason;
}
