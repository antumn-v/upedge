package com.upedge.sms.modules.wholesale.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gx
 */
@Data
public class WholesaleOrderItem{

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
    private Long cartId;
	/**
	 * 
	 */
    private Integer quantity;
	/**
	 * 
	 */
    private Integer dischargeQuantity;

	private Integer totalStock;
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
	/**
	 * 
	 */
    private BigDecimal variantWeight;
	/**
	 * 
	 */
    private BigDecimal variantVolume;

}
