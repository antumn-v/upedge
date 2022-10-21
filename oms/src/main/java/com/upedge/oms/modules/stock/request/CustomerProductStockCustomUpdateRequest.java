package com.upedge.oms.modules.stock.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerProductStockCustomUpdateRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private String warehouseCode;

    @NotNull
    private Long variantId;

    @NotNull
    private Integer quantity;
}
