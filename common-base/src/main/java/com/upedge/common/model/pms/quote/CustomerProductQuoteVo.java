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
    private String storeProductSku;
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
    /**
     *
     */
    private String variantImage;
    /**
     *
     */
    private BigDecimal variantPrice;
    /**
     *
     */
    private Long quoteApplyId;
}
