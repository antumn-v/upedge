package com.upedge.pms.modules.purchase.vo;

import lombok.Data;

@Data
public class PurchaseItemVo {

    private Integer id;

    private String purchaseSku;
    /**
     *
     */
    private Long productId;
    /**
     *
     */
    private Long variantId;
    /**
     *
     */
    private String variantSku;
    /**
     *
     */
    private String cnName;
    /**
     *
     */
    private String variantImage;
    /**
     *
     */
    private String purchaseLink;
    /**
     *
     */
    private Integer quantity;
    /**
     *
     */
    private String specId;

    private double price;
}
