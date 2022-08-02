package com.upedge.pms.modules.purchase.dto;

import lombok.Data;

@Data
public class PurchaseOrderListDto {

    private Long purchaseId;

    private String trackingCode;

    private String supplierName;

    private String purchaseStatus;

    private Integer purchaseState;

    private String variantSku;

    private String variantName;

    private String purchaseSku;

    private String barcode;

}
