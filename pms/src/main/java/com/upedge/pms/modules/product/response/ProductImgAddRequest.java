package com.upedge.pms.modules.product.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductImgAddRequest {

    @NotBlank
    private String img;

}
