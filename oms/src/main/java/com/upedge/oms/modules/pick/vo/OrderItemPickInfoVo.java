package com.upedge.oms.modules.pick.vo;

import lombok.Data;

@Data
public class OrderItemPickInfoVo {

    private String platOrderName;

    private String storeName;

    private Long itemId;

    private Long variantId;

    private String variantSku;

    private String variantImage;

    private String variantName;

    private String barcode;

    private Integer quantity;

    private Integer pickedQuantity;

    private String storeVariantImage;

    private String storeVariantSku;

    private String storeVariantName;
}
