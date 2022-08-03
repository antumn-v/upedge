package com.upedge.oms.modules.order.vo;

import com.upedge.oms.modules.order.entity.OrderItem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderPickTypeVo {

    Long orderId;

    Integer pickType;

    List<OrderItem> items = new ArrayList<>();
}
