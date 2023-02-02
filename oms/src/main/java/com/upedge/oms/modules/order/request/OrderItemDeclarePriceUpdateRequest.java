package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderItemDeclarePriceUpdateRequest {

    @NotNull
    private Long itemId;

    @NotNull
    private BigDecimal declarePrice;
}
