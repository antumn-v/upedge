package com.upedge.pms.modules.quote.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
    private BigDecimal quotePrice;
	/**
	 * 
	 */
    private Long quoteApplyId;

}
