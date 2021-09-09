package com.upedge.pms.modules.product.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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

}
