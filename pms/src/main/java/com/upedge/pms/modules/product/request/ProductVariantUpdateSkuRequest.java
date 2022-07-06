package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductVariantUpdateSkuRequest {

    @NotNull
    private Long id;

    @NotBlank(message = "sku不能为空")
    private String sku;
}
