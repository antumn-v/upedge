package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class VariantUpdateVolumeRequest {
    @NotNull
    BigDecimal width;

    @NotNull
    BigDecimal length;

    @NotNull
    BigDecimal height;

    Long variantId;

    List<Long> variantIds;

    @NotNull
    Long productId;
}
