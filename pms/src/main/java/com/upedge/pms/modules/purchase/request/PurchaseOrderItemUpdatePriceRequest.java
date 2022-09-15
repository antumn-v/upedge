package com.upedge.pms.modules.purchase.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class PurchaseOrderItemUpdatePriceRequest {

    @NotNull
    private Long id;

    @NotNull
    private BigDecimal price;
}
