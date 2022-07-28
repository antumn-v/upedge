package com.upedge.pms.modules.purchase.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VariantSafeStockUpdateRequest {

    @NotNull
    private Long variantId;

    @NotBlank
    private String warehouseCode;

    @NotNull
    private Integer safeStock;
}
