package com.upedge.common.model.order.vo;

import lombok.Data;

@Data
public class OrderItemUpdateImageNameRequest {

    Long storeVariantId;

    String image;

    String name;
}
