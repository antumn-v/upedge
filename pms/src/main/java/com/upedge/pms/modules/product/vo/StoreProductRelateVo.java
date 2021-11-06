package com.upedge.pms.modules.product.vo;

import lombok.Data;

@Data
public class StoreProductRelateVo {

    private Long storeVariantId;

    private Long variantId;

    private String storeVariantName;

    private String storeVariantSku;

    private String storeVariantImage;

    private Integer scale;

    private String variantName;

    private String variantSku;

    private String variantImage;
}
