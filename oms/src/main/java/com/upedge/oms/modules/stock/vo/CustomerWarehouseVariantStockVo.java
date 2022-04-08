package com.upedge.oms.modules.stock.vo;

import lombok.Data;

@Data
public class CustomerWarehouseVariantStockVo {

    Long customerId;

    String warehouseCode;

    Long variantId;

    Integer stock;
}
