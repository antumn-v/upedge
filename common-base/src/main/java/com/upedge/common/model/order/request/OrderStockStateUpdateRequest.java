package com.upedge.common.model.order.request;

import lombok.Data;

@Data
public class OrderStockStateUpdateRequest {

    private Long orderId;

    private Integer stockState;
}
