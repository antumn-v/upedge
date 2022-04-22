package com.upedge.sms.modules.wholesale.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class WholesaleOrderPayRequest {

    @NotNull
    private Long orderId;

    @NotNull
    Integer shipType;

    @NotNull
    BigDecimal shipPrice;

    @NotNull
    BigDecimal payAmount;
}
