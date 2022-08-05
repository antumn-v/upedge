package com.upedge.oms.modules.pick.vo;

import lombok.Data;

@Data
public class OrderPickQuantityVo {

    private Long orderId;

    private Integer skuQuantity;

    private Integer skuType;

    private Integer pickType;
}
