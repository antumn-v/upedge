package com.upedge.thirdparty.shopify.moudles.shop.entity;

import lombok.Data;

import java.util.List;

@Data
public class ShopifyWebhook {


    private Long id;
    private String address;
    private String topic;
    private String created_at;
    private String updated_at;
    private String format = "json";
    private String api_version;



}
