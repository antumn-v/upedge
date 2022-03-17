package com.upedge.pms.modules.quote.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gx
 */
@Data
public class CustomerProductQuote{

	/**
	 * 
	 */
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

	private Integer quoteState;

	private Integer quoteScale;

}
