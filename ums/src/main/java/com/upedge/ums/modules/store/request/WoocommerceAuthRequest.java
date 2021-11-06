package com.upedge.ums.modules.store.request;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WoocommerceAuthRequest {

    @NotNull
    private String storeUrl;

    @NotNull
    private String apiKey;

    @NotNull
    private String apiSecret;
}
