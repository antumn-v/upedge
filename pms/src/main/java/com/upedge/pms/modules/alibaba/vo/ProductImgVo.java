package com.upedge.pms.modules.alibaba.vo;

import lombok.Data;

/**
 * @author author
 */
@Data
public class ProductImgVo{

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

}
