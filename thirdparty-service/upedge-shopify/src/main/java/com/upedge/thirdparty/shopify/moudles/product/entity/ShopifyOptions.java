package com.upedge.thirdparty.shopify.moudles.product.entity;

import lombok.Data;

import java.util.List;

@Data
public class ShopifyOptions {

    private String id;
    private String product_id;
    private String name;
    private Integer position;
    private List<String> values;
}
