package com.upedge.pms.modules.purchase.vo;

import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import lombok.Data;

@Data
public class VariantWarehouseStockVo  extends VariantWarehouseStock {

    private int preSaleQuantity;

    private String cnName;

    private String variantSku;

    private String purchaseSku;

    private String variantImage;
}
