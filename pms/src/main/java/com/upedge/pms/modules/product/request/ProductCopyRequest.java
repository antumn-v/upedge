package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductCopyRequest {
    @NotNull
    private Long id;

}
