package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class UserProductStock{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long userId;
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
    private Long warehouseId;
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

}
