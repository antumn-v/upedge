package com.upedge.common.model.oms.stock;

import lombok.Data;

@Data
public class CustomerStockVo {

    private Long variantId;

    private Integer stock;

    private Integer totalStock;
}
