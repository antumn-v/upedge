package com.upedge.pms.modules.purchase.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VariantStockUpdateRequest {

    @NotNull
    Long variantId;

    @NotBlank
    String warehouseCode;

    @NotNull
    Integer stock;

    String remark;


}
