package com.upedge.common.model.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 海桐
 */
@Data
public class VariantDetail {

    /**
     * 产品ID
     */
    private Long productId;
    /**
     *
     */
    private String productTitle;

    private Long productShippingId;
    /**
     * 变体ID
     */
    private Long variantId;
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
     * 单价
     */
    private BigDecimal cnyPrice;

    private BigDecimal usdPrice;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private BigDecimal volume;

    private BigDecimal width;

    private BigDecimal length;

    private BigDecimal height;
}
