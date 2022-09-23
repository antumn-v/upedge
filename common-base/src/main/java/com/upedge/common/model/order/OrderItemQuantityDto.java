package com.upedge.common.model.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemQuantityDto {

    private Long variantId;

    private Long paymentId;

    private Long customerId;

    private Long orderId;

    private Integer stockType;

    private List<Long> orderIds;


}
