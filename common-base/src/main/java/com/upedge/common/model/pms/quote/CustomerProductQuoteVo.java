package com.upedge.common.model.pms.quote;

import com.upedge.common.model.product.VariantDetail;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class CustomerProductQuoteVo extends VariantDetail {


    private Long customerId;
    /**
     *
     */
    private Long storeVariantId;
    /**
     *
     */
    private Long storeProductId;
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
    private String storeVariantName;
    /**
     *
     */
    private String storeProductTitle;
    /**
     *
     */
    private String storeVariantSku;
    /**
     *
     */
    private String storeVariantImage;
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

    private BigDecimal width;

    private BigDecimal length;

    private BigDecimal height;
    /**
     *
     */
    private String variantImage;

    private BigDecimal quotePrice;

    private Integer quoteScale;
    /**
     *
     */
    private Long quoteApplyId;

    private Integer quoteType;

    private Integer quoteState;
}
