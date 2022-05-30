package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class OrderRefundItem{

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
    private Integer quantity;

	private Integer costRefundQuantity;

	private Integer costPayQuantity;

	private Integer stockDischargeQuantity;

	private Integer stockRefundQuantity;
	/**
	 * 
	 */
    private BigDecimal price;
	/**
	 * 
	 */
    private Long refundId;
	/**
	 * 
	 */
    private Long orderItemId;
	/**
	 * 
	 */
    private String variantSku;
	/**
	 * 
	 */
    private String variantImage;

}
