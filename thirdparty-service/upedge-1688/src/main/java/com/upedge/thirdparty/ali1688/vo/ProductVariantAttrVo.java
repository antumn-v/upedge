package com.upedge.thirdparty.ali1688.vo;

import lombok.Data;

/**
 * @author author
 */
@Data
public class ProductVariantAttrVo{

	/**
	*
	*/
	private Long id;
	/**
	* 中文名称
	*/
	private String variantAttrCname;
	/**
	*
	*/
	private String variantAttrEname;
	/**
	*
	*/
	private String originalAttrCvalue;
	/**
	* 中文值
	*/
	private String variantAttrCvalue;
	/**
	*
	*/
	private String variantAttrEvalue;
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
	private Integer seq;

}
