package com.upedge.ums.modules.store.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ShopifyAuthRequest {

    @NotBlank
    String shopName;
}
