package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductUploadImageRequest {

    @NotNull
    Long productId;

    @NotBlank
    String base64Img;
}
