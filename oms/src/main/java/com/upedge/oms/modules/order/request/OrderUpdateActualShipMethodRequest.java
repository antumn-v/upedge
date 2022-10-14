package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderUpdateActualShipMethodRequest {

    @NotNull
    private Long orderId;

    @NotNull
    private Long shipMethodId;
}
