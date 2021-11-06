package com.upedge.common.model.old.pms;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class AdminProductCategory{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 商品id
	 */
    private Long productId;
	/**
	 * 商品类别id
	 */
    private Long categoryId;

}
