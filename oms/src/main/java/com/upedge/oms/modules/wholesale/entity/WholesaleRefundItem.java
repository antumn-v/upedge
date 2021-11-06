package com.upedge.oms.modules.wholesale.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class WholesaleRefundItem{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private Integer quantity;
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
