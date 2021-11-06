package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetAliProductRequest {

    @NotBlank
    private String url;

}
