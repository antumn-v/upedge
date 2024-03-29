package com.upedge.pms.modules.purchase.vo;

import lombok.Data;

@Data
public class VariantStockListDto {

    private Long customerId;

    private Long storeId;

    private String variantSku;

    private String purchaseSku;

    private String barcode;

    private Long id;

    private String variantName;

    private String cnName;

    private String beginTime;

    private String endTime;

}
