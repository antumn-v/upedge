package com.upedge.common.model.order.dto;

import lombok.Data;

@Data
public class OrderItemPurchaseAdviceDto {

    private Long storeVariantSku;

    private Long storeProductId;

    private Long orderId;

    private Long customerId;

    private Long adminVariantId;

    private Long adminProductId;


}
