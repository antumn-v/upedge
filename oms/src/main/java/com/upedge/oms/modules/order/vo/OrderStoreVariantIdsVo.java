package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderStoreVariantIdsVo {

    Long orderId;

    List<Long> storeVariantIds;
}
