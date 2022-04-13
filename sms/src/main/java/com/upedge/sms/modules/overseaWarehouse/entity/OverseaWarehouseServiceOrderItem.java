package com.upedge.sms.modules.overseaWarehouse.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author gx
 */
@Data
public class OverseaWarehouseServiceOrderItem{

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
    private String productTitle;
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
    private String warehouseSku;
	/**
	 * 
	 */
    private BigDecimal variantWeight;
	/**
	 * 
	 */
    private BigDecimal length;
	/**
	 * 
	 */
    private BigDecimal height;
	/**
	 * 
	 */
    private BigDecimal width;

}
