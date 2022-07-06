package com.upedge.pms.modules.product.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gx
 */
@Data
public class ProductVariant{

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
	 * 
	 */
    private BigDecimal usdPrice;
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
	/**
	 * 变体中文名
	 */
    private String cnName;
	/**
	 * 变体英文名
	 */
    private String enName;
	/**
	 * 长
	 */
    private BigDecimal length;
	/**
	 * 宽
	 */
    private BigDecimal width;
	/**
	 * 高
	 */
    private BigDecimal height;

	private String saiheSku;

	private BigDecimal latestQuotePrice;

	private String purchaseSku;

	private List<ProductVariantAttr> productVariantAttrList=new ArrayList<>();

}
