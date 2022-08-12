package com.upedge.oms.modules.pick.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderPrintVo {

    private Integer packageCount;

    private Integer skuType;

    private Integer skuQuantity;

    private List<OrderItemPickInfoVo> orderItemPickInfoVos;
}
