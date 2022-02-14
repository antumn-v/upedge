package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductVariantUpdateVolumeWeightRequest {

    @NotNull
    private BigDecimal length;

    @NotNull
    private BigDecimal width;

    @NotNull
    private BigDecimal height;
    /**
     * 变体ids
     */
    @Size(min = 1)
    private List<Long> ids;
}
