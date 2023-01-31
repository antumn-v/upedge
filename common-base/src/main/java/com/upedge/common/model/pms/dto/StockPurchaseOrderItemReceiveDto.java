package com.upedge.common.model.pms.dto;

import lombok.Data;

@Data
public class StockPurchaseOrderItemReceiveDto {

    private Long orderId;

    private Long variantId;

    private Integer quantity;
}
