package com.upedge.thirdparty.woocommerce.entity;

import lombok.Data;

@Data
public class AuthParam {
    String shopName;
    String apiKey;
    String apiSecret;

    String token;

    String version = "v3";
}
