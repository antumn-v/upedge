package com.upedge.thirdparty.ali1688.vo;

import lombok.Data;

/**
 * @author author
 */
@Data
public class ProductAttributeVo{

	/**
	*
	*/
	private Long id;
	/**
	* 商品id
	*/
	private Long productId;
	/**
	* 物流属性id
	*/
	private Long shippingAttributeId;
	/**
	* 1688目录id
	*/
	private Long aliCnCategoryId;
	/**
	 *1688目录
	 */
	private String aliCnCategoryName;
	/**
	* 产品货号
	*/
	private String itemNo;
	/**
	* 1688上30天成交量
	*/
	private Integer turnover;
	/**
	* 1688上的评分
	*/
	private Integer score;
	/**
	* 报关英文名
	*/
	private String entryEname;
	/**
	* 报关中文名
	*/
	private String entryCname;


}
