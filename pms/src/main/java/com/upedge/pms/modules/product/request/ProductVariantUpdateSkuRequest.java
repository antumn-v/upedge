package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductVariantUpdateSkuRequest {

    @NotNull
    private Long id;

    private String variantSku;

    private String purchaseSku;
}
