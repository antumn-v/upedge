package com.upedge.thirdparty.shopify.moudles.order.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopifyTransaction {

    private String id;
    private String order_id;
    private String kind;
    private String gateway;
    private String status;
    private String message;
    private String created_at;
    private String processed_at;
    private String source_name;
    private BigDecimal amount;
    private String currency;
}
