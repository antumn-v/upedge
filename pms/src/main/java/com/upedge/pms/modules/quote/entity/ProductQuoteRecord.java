package com.upedge.pms.modules.quote.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class ProductQuoteRecord{

	/**
	 * 
	 */
    private Integer id;
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
    private Long storeVariantId;
	/**
	 * 
	 */
    private Long storeProductId;
	/**
	 * 
	 */
    private BigDecimal quotePrice;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private Date createTime;

}
