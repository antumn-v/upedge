package com.upedge.thirdparty.ali1688.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
@Data
public class ProductVariantVo{

	/**
	*
	*/
	private Long id;
	/**
	*
	*/
	private String originalVariantId;
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

	private String specId;

	private List<ProductVariantAttrVo> variantAttrVoList;

}
