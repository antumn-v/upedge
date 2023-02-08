package com.upedge.pms.modules.purchase.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Create1688PurchaseOrderRequest {

    @NotNull
    private Long orderId;

    private String message;
}
