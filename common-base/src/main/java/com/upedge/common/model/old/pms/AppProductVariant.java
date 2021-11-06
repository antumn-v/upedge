package com.upedge.common.model.old.pms;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xwCui
 */
@Data
public class AppProductVariant{

	/**
	 * 
	 */
    private Long appVariantId;
	/**
	 * 
	 */
    private Long adminVariantId;
	/**
	 * 
	 */
    private Long productId;
	/**
	 * 已上传的变体所对应的新ID
	 */
    private Long storeVariantId;
	/**
	 * 已上传商品新生成的变体Id所对应的productId
	 */
    private Long storeProductId;
	/**
	 * 初始VariantId
	 */
    private String originalVariantId;
	/**
	 * 
	 */
    private String originalProductId;
	/**
	 * 商品SKU
	 */
    private String productSku;
	/**
	 * 商品颜色
	 */
    private String productAttrAName;
	/**
	 * 商品尺寸
	 */
    private String productAttrAValue;
	/**
	 * 
	 */
    private String productAttrBName;
	/**
	 * 
	 */
    private String productAttrBValue;
	/**
	 * 
	 */
    private String productAttrCName;
	/**
	 * 
	 */
    private String productAttrCValue;
	/**
	 * 
	 */
    private String variantImage;
	/**
	 * 产品成本
	 */
    private BigDecimal productCost;
	/**
	 * 原价
	 */
    private BigDecimal originalPrice;
	/**
	 * 现价
	 */
    private BigDecimal presentPrice;
	/**
	 * 库存
	 */
    private Long productInventory;
	/**
	 * 产品重量
	 */
    private BigDecimal productWeight;
	/**
	 * 
	 */
    private Integer status;
	/**
	 * 订单产品数量
	 */
    private Long storeQuantity;
	/**
	 * 产品来源：ali,sib
	 */
    private String source;
	/**
	 * ali变体ID
	 */
    private String aliVariantId;
	/**
	 * 发货国家代码
	 */
    private String countryCode;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private Integer scale;
	/**
	 * 0:正常产品 1捆绑产品
	 */
    private Integer variantType;

}
