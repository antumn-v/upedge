package com.upedge.common.model.oms.order;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemQuantityVo {

    private Long orderId;

    private String warehouseCode;

    private Long operatorId;

    private List<ItemQuantityVo> itemQuantityVos;
}
