package com.upedge.common.model.old.pms;

import lombok.Data;

/**
 * @author xwCui
 */
@Data
public class AdminProductImg{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 商品id
	 */
    private Long productId;
	/**
	 * 商品图片
	 */
    private String imageUrl;
	/**
	 * 1:启用/0:禁用
	 */
    private Integer state;
	/**
	 * 图片排序
	 */
    private Integer imageSeq;
	/**
	 * 1:主图在用或变体再用
	 */
    private Integer imageType;

}
