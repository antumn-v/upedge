package com.upedge.oms.modules.order.vo;

import com.upedge.oms.modules.order.entity.OrderAddress;
import lombok.Data;

import java.util.List;

@Data
public class OrderDetailsVo {

    private OrderVo order;

    private List<OrderItemVo> orderItems;

    private OrderAddress orderAddress;

    private List<OrderStoreVo> orderStores;

}
