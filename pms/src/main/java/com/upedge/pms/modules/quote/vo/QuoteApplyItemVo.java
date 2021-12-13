package com.upedge.pms.modules.quote.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QuoteApplyItemVo {

    private Long id;
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
    private Long storeProductId;
    /**
     *
     */
    private Long storeVariantId;
    /**
     *
     */
    private String storeVariantName;
    /**
     *
     */
    private String storeProductTitle;
    /**
     *
     */
    private String storeProductSku;
    /**
     *
     */
    private String storeVariantImage;

    private String storeProductHandle;
    /**
     *
     */
    private String productTitle;
    /**
     *
     */
    private String variantName;
    /**
     *
     */
    private String variantSku;
    /**
     *
     */
    private String variantImage;
    /**
     *
     */
    private BigDecimal quotePrice;
    /**
     *
     */
    private Long quoteApplyId;
}
