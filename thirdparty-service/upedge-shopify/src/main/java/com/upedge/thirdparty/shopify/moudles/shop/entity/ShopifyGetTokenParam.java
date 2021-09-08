package com.upedge.thirdparty.shopify.moudles.shop.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ShopifyGetTokenParam {

    private String apiVersion = "2020-04";
    @NotNull
    private String shopName;
    @NotNull
    private String apiKey;
    @NotNull
    private String apiSecret;
    @NotNull
    private String code;

}
