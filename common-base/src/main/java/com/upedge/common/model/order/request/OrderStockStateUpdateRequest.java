package com.upedge.common.model.order.request;

import com.upedge.common.model.oms.order.ItemQuantityVo;
import lombok.Data;

import java.util.List;

@Data
public class OrderStockStateUpdateRequest {

    private Long orderId;

    private Integer stockState;

    private List<ItemQuantityVo> itemQuantityVos;
}
