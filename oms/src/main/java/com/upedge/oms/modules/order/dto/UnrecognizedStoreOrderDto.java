package com.upedge.oms.modules.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class UnrecognizedStoreOrderDto {

    Long orgId;

    Long storeProductId;

    String orderName;

    List<Long> orgIds;
}
