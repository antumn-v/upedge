package com.upedge.common.model.pms.vo;

import lombok.Data;

@Data
public class PurchaseAdviceItemVo {

    private Long variantId;
    //预购数量
    private int orderQuantity;
    //锁定库存
    private int lockQuantity;
    //安全库存
    private int safeStock;
    //锁定库存
    private int availableStock;
    //采购中库存
    private int purchaseQuantity;

    private String variantImage;

    private String variantSku;

    private String cnName;

    private String purchaseSku;
    //建议备库数量
    private int suggestQuantity;

    private Long customerId;

    private String barcode;

    private Integer aliInventory;




}
