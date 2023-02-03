package com.upedge.pms.modules.purchase.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PurchaseOrderItemUpdateQuantityRequest {


    @NotNull
    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer requireQuantity;
}
