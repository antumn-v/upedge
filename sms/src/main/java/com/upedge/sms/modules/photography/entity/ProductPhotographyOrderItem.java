package com.upedge.sms.modules.photography.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author gx
 */
@NoArgsConstructor
@Data
public class ProductPhotographyOrderItem{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Long variantId;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 
	 */
    private String productTitle;
	/**
	 * 
	 */
    private String variantSku;
	/**
	 * 
	 */
    private String variantName;
	/**
	 * 
	 */
    private String variantImage;
	/**
	 * 
	 */
    private BigDecimal price;

}
