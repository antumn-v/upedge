package com.upedge.pms.modules.purchase.vo;

import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import lombok.Data;

@Data
public class VariantWarehouseStockRecordVo extends VariantWarehouseStockRecord {

    private String cnName;

    private String variantSku;

    private String purchaseSku;

    private String variantImage;

    private String barcode;
}
