package com.upedge.common.model.order;

import lombok.Data;

@Data
public class OrderItemQuantityDto {

    private Long variantId;

    private Long paymentId;

    private Long customerId;

    private Long orderId;


}
