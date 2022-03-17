package com.upedge.pms.modules.quote.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gx
 */
@Data
public class QuoteApplyItem{

	/**
	 * 
	 */
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
	/**
	 * 
	 */
    private String variantImage;
	/**
	 * 
	 */
    private BigDecimal quotePrice;

	private Integer quoteScale;
	/**
	 * 
	 */
    private Long quoteApplyId;

	private Integer state;

}
