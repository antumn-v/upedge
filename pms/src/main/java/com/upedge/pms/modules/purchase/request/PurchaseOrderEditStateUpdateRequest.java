package com.upedge.pms.modules.purchase.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PurchaseOrderEditStateUpdateRequest {

    @NotNull
    private Long id;

    @NotNull
    private Integer editState;
}
