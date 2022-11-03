package com.upedge.common.model.oms.order;

import lombok.Data;

@Data
public class ItemQuantityVo {

    private Long itemId;

    private Long variantId;
    //总数量
    private Integer quantity;
    //新分配库存数量
    private Integer lockQuantity;
    //已锁定库存数量
    private Integer lockedQuantity;
    //未锁定库存数量
    private Integer unLockQuantity;
}
