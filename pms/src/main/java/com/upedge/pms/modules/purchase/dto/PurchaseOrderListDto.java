package com.upedge.pms.modules.purchase.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderListDto {

    private Long id;

    private Long purchaseId;

    private String trackingCode;

    private String supplierName;

    private String purchaseStatus;

    private Integer purchaseType;

    private Integer purchaseState;

    private Long productId;

    private String purchaseLink;

    private String variantSku;

    private String variantName;

    private String purchaseSku;

    private String barcode;

    private List<Long> ids;

}
