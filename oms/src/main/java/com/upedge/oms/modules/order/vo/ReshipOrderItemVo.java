package com.upedge.oms.modules.order.vo;

import lombok.Data;

@Data
public class ReshipOrderItemVo {

    private Long itemId;//原始orderVariantId

    private Long adminVariantId;

    private Integer quantity;//用户数量

}
