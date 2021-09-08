package com.upedge.thirdparty.shopify.moudles.order.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopifyLineItem {

    private String id;
    private String variant_id;
    private String title;
    private Integer quantity;
    private String sku;
    private String variant_title;
    private String vendor;
    private String fulfillment_service;
    private String product_id;
    private Boolean requires_shipping;
    private Boolean taxable;
    private Boolean gift_card;
    private String name;
    private String variant_inventory_management;
    private Boolean product_exists;
    private Integer fulfillable_quantity;
    private Integer grams;
    private BigDecimal price;
    private String total_discount;
    private String fulfillment_status;
}
