package com.upedge.oms.modules.pack.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderPackRevokeRequest {

    @NotNull
    private Long orderId;

    private String reason;
}
