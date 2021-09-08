package com.upedge.thirdparty.shopify.moudles.shop.entity;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class ShopifyRequestParam {

    private String apiVersion = "2021-01";
    @NotNull
    private String shopName;
    @NotNull
    private String accessToken;
    @NotNull
    private String apiKey;
    @NotNull
    private String apiSecret;

    private String token;
    private String scope;
    private String param;
    private String nextUrl;
}
