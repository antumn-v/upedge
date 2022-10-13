package com.upedge.thirdparty.shopify.moudles.product.entity;

import lombok.Data;

import java.util.List;

@Data
public class ShopifyImage {

    private String id;
    private String product_id;
    private Integer position;
    private String created_at;
    private String updated_at;
    private Object alt;
    private Integer width;
    private Integer height;
    private String src;
    private String admin_graphql_api_id;
    private List<String> variant_ids;
}
