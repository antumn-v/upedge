package com.upedge.ums.modules.account.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ConfirmTransferRechargeRequest {

    @NotNull
    private Long id;

}
