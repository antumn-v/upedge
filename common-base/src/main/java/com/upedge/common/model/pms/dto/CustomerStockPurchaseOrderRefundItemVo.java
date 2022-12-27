package com.upedge.common.model.pms.dto;

import lombok.Data;

@Data
public class CustomerStockPurchaseOrderRefundItemVo {

    private Long itemId;

    private Long variantId;

    private Integer refundQuantity;
}
