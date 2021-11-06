package com.upedge.oms.modules.sales.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class CustomerProductSalesLog{

	/**
	 * 
	 */
    private Long id;
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
    private Integer quantity;
	/**
	 * 
	 */
    private Long customerId;
	/**
	 * 
	 */
    private Long orderId;

    private Long orderItemId;
	/**
	 * 
	 */
    private Integer orderType;
	/**
	 * 
	 */
    private Integer state;
	/**
	 * 
	 */
    private Date createTime;

}
