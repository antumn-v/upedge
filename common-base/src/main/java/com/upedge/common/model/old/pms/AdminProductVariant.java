package com.upedge.common.model.old.pms;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xwCui
 */
@Data
public class AdminProductVariant{

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
	 * 中文名称
	 */
    private String variantAttrACname;
	/**
	 * 
	 */
    private String variantAttrAName;
	/**
	 * 
	 */
    private String originalACvalue;
	/**
	 * 中文名称
	 */
    private String variantAttrACvalue;
	/**
	 * 
	 */
    private String variantAttrAValue;
	/**
	 * 中文名称
	 */
    private String variantAttrBCname;
	/**
	 * 
	 */
    private String variantAttrBName;
	/**
	 * 
	 */
    private String originalBCvalue;
	/**
	 * 
	 */
    private String variantAttrBCvalue;
	/**
	 * 
	 */
    private String variantAttrBValue;
	/**
	 * 
	 */
    private String variantAttrCName;
	/**
	 * 
	 */
    private String variantAttrCValue;
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
	 * 变体名称
	 */
    private String variantName;
	/**
	 * 变体中文名
	 */
    private String variantCname;
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

}
