package com.upedge.common.model.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RelateVariantVo  {

    private Long variantId;

    private Long productId;

    private BigDecimal usdPrice;

    private BigDecimal cnyPrice;
    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private BigDecimal volume;


    private String image;

    private String sku;

    private Integer variantType;

}
