package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 海桐
 */
@Data
public class ProductImportCartRequest {

    @NotNull
    private Long variantId;

    @NotNull
    private Integer quantity;

    /**
     * 备库=0，批发=1
     */
    @NotNull
    private Integer cartType;

}
