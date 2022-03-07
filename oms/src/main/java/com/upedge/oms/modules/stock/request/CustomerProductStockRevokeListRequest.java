package com.upedge.oms.modules.stock.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerProductStockRevokeListRequest {

    @NotNull
    Long customerId;

    @NotNull
    Long variantId;

    @NotNull
    String warehouseCode;
}
