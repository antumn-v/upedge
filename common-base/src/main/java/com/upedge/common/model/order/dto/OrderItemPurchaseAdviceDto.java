package com.upedge.common.model.order.dto;

import lombok.Data;

@Data
public class OrderItemPurchaseAdviceDto {

    private Long storeVariantSku;

    private Long storeProductId;

    private Long orderId;

    private Long customerId;

    private Long storeId;

    private Long adminVariantId;

    private Long adminProductId;

    private String barcode;

    private String variantSku;



}
