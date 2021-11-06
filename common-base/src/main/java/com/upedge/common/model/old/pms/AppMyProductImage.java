package com.upedge.common.model.old.pms;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class AppMyProductImage{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long originalProductId;
	/**
	 * 
	 */
    private Long originalImageId;
	/**
	 * 对应店铺产品ID
	 */
    private Long productId;
	/**
	 * 图片SRC
	 */
    private String src;
	/**
	 * 
	 */
    private Integer position;

}
