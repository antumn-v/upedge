package com.upedge.common.model.pms.vo;

import lombok.Data;

@Data
public class PurchaseAdviceItemVo {

    private Long variantId;

    private int orderQuantity;

    private int stockQuantity;

    private int safeStock;

    private int purchaseQuantity;

    private String variantImage;

    private String variantSku;

    private String cnName;

    private String purchaseSku;

    private int suggestQuantity;


}
