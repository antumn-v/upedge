package com.upedge.pms.modules.product.vo;

import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import lombok.Data;

@Data
public class VariantWarehouseStockVo  extends VariantWarehouseStock {

    private int preSaleQuantity;
}
