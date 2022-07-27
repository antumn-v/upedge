package com.upedge.pms.modules.purchase.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gx
 */
@Data
public class PurchaseOrderItem{

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
    private String purchaseLink;
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
    private String variantSku;
	/**
	 * 
	 */
    private String purchaseSku;
	/**
	 * 
	 */
    private Integer quantity;

	private Integer receiveQuantity;
	/**
	 * 
	 */
    private BigDecimal price;
	/**
	 * 
	 */
    private String specId;

	private Long barcode;

	private BigDecimal weight;

}
