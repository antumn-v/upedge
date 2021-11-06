package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AppVariantShipsRequest {

    @NotNull
    private Long variantId;

    BigDecimal quantity = BigDecimal.ONE;

    @NotNull
    private Long toAreaId;
}
