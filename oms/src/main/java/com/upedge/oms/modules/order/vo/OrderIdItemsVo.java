package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderIdItemsVo {
    Long storeOrderId;

    Long orderId;

    List<AppOrderItemVo> itemVos;
}
