package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xwCui
 */
@Data
public class WholesaleOrderVariant{

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
	/**
	 * 
	 */
    private BigDecimal cost;
	/**
	 * 
	 */
    private BigDecimal variantWeight;
	/**
	 * 
	 */
    private Long shipTemplateId;

}
