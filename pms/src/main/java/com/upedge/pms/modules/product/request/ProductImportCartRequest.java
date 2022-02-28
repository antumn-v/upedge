package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 海桐
 */
@Data
public class ProductImportCartRequest {

    @NotNull
    private  Long productId;

    @NotNull
    private Long variantId;

    @NotNull
    private Integer quantity;


    private String image;

}
