package com.upedge.pms.modules.quote.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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
