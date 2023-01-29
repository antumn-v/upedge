package com.upedge.oms.modules.pick.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderPrintVo {

    private Integer waveNo;

    private Integer packageCount;

    private Integer skuType;

    private Integer skuQuantity;

    private String shipMethodName;

    private List<OrderItemPickInfoVo> orderItemPickInfoVos;
}
