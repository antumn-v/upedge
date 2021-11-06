package com.upedge.common.model.pms.request;

import lombok.Data;

import java.util.List;


@Data
public class OrderQuoteApplyRequest {

    Long userId;

    Long customerId;

    Long storeOrderId;

    Long orderId;

    Long storeId;

    List<Long> storeVariantId;
}
