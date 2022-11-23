package com.upedge.pms.modules.quote.entity;

import com.upedge.pms.modules.product.entity.StoreProductVariant;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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

	private Long storeParentVariantId;

	private Date updateTime;

	private String originalId;

	private String storeName;

	public CustomerProductQuote(StoreProductVariant storeProductVariant) {
		this.storeProductId = storeProductVariant.getProductId();
		this.storeProductTitle = storeProductVariant.getTitle();
		this.storeVariantImage = storeProductVariant.getImage();
		this.storeVariantName = storeProductVariant.getTitle();
		this.storeVariantSku = storeProductVariant.getSku();
		this.storeVariantId = storeProductVariant.getId();
		this.storeParentVariantId = storeProductVariant.getParentVariantId();
		this.updateTime = new Date();
	}

	public CustomerProductQuote() {
	}
}
