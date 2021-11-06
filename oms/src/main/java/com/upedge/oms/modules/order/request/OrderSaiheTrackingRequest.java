package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderSaiheTrackingRequest {

    @NotNull
    private Long orderId;

}
