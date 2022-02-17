package com.upedge.oms.modules.stock.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class StockOrderUpdateShipRequest {

    @NotNull
    Long orderId;

    @NotNull
    String shipMethod;

    @NotNull
    BigDecimal shipPrice;
}
