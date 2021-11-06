package com.upedge.common.model.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class ProductVariantTo {

	/**
	*
	*/
	private Long id;
	/**
	*
	*/
	private Long originalVariantId;
	/**
	* 商品id
	*/
	private Long productId;
	/**
	* 变体sku
	*/
	private String variantSku;
	/**
	* 变体图片
	*/
	private String variantImage;
	/**
	* 变体价格
	*/
	private BigDecimal variantPrice;
	/**
	* 变体库存
	*/
	private Long inventory;
	/**
	* 重量
	*/
	private BigDecimal weight;
	/**
	* 1:可用0:禁用
	*/
	private Integer state;
	/**
	* 体积重
	*/
	private BigDecimal volumeWeight;
	/**
	* 0:正常产品 1:捆绑产品
	*/
	private Integer variantType;

	private String cnName;

	private String enName;
}
