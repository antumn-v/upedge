package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateProductInfoRequest {

    @NotBlank
    private String productDesc;

}
