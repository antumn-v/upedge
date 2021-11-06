package com.upedge.ums.modules.account.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplyRechargeCancelRequest {

    @NotNull
    private String rejectReason;

}
