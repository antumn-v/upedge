package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xwCui
 */
@Data
public class AppRefundItem{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long upedgeOrderId;
	/**
	 * 
	 */
    private Long storeOrderId;
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

}
