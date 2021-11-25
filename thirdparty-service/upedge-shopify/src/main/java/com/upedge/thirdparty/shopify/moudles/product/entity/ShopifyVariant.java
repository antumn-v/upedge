package com.upedge.thirdparty.shopify.moudles.product.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopifyVariant {

    private String id;
    private String product_id;
    private String title;
    private BigDecimal price;
    private String sku;
    private Integer position;
    private String inventory_policy;
    private BigDecimal compare_at_price;
    private String fulfillment_service;
    private String inventory_management = "shopify";
    private String option1;
    private String option2;
    private String option3;
    private String created_at;
    private String updated_at;
    private Boolean taxable;
    private String barcode;
    private Integer grams;
    private String image_id;
    private BigDecimal weight;
    private String weight_unit = "g";
    private Long inventory_item_id;
    private Long inventory_quantity;
    private Long old_inventory_quantity;
    private Boolean requires_shipping = true;
    private String admin_graphql_api_id;
}
