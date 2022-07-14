package com.upedge.common.model.oms.order;

import lombok.Data;

@Data
public class ItemQuantityVo {

    private Long itemId;

    private Long variantId;

    private Integer quantity;
}
