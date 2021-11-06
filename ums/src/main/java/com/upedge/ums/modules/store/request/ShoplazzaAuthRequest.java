package com.upedge.ums.modules.store.request;

import lombok.Data;

@Data
public class ShoplazzaAuthRequest {

    private String storeUrl;

    private String accessToken;
}
