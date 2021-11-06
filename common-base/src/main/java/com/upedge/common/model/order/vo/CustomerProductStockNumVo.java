package com.upedge.common.model.order.vo;

import lombok.Data;

@Data
public class CustomerProductStockNumVo {
    private String variantSku;

    private Long customerStock;
}
