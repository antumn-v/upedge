package com.upedge.common.model.pms.dto;

import lombok.Data;

@Data
public class VariantPurchaseInfoDto {

    private Long variantId;

    private String purchaseSku;

    private String supplierName;

    private String barcode;
}
