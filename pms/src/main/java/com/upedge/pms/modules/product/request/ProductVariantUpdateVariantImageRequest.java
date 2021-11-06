package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProductVariantUpdateVariantImageRequest {

    /**
     * 体积重
     */
    @NotBlank
    private String variantImage;
    /**
     * 变体ids
     */
    @Size(min = 1)
    private List<Long> ids;

}
