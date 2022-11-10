package com.upedge.common.model.oms.order;

import lombok.Data;

@Data
public class OrderStockClearRequest {

    private Long variantId;

    private String warehouseCode;
}
