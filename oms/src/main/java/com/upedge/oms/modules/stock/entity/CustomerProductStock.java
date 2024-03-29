package com.upedge.oms.modules.stock.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class CustomerProductStock{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
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
    private Integer stock;
	/**
	 * 
	 */
    private String warehouseCode;

	private String warehouseName;
	/**
	 * 被锁定的库存
	 */
    private Integer lockStock;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private String variantImage;

	private String variantSku;

	private String productTitle;

	private String variantName;

	private Integer stockType;

	private Integer customerShowState;

}
