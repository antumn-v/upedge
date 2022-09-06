package com.upedge.pms.modules.purchase.vo;

import lombok.Data;

@Data
public class VariantStockListDto {

    private String variantSku;

    private String purchaseSku;

    private String barcode;

    private Long id;

    private String variantName;

}
