package com.upedge.common.model.pms.request;

import lombok.Data;

@Data
public class VariantStockRestoreLockQuantityRequest {

    private Long variantId;

    private String warehouseStock;

    private Integer lockQuantity;
}
