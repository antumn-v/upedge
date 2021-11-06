package com.upedge.pms.modules.product.entity;

import lombok.Data;

/**
 * @author author
 */
@Data
public class StoreProductImage{

	/**
	 * 
	 */
private Long id;
	/**
	 * 
	 */
private String platImageId;
	/**
	 * 
	 */
private String platProductId;
	/**
	 * 对应店铺产品ID
	 */
private Long productId;
	/**
	 * 图片SRC
	 */
private String src;

}
