package com.upedge.common.model.old.pms;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author xwCui
 */
@Data
public class AppMyProductVariant{

	/**
	 * 变体ID
	 */
    private Long id;
	/**
	 * 变体对应的产品ID
	 */
    private Long productId;
	/**
	 * 
	 */
    private Long originalVariantId;
	/**
	 * 
	 */
    private Long originalProductId;
	/**
	 * 
	 */
    private String title;
	/**
	 * 售价
	 */
    private BigDecimal price;
	/**
	 * 原价
	 */
    private BigDecimal compareAtPrice;
	/**
	 * 
	 */
    private String sku;

    private String image;
	/**
	 * 
	 */
    private Integer position;
	/**
	 * 
	 */
    private String option1;
	/**
	 * 
	 */
    private String option2;
	/**
	 * 
	 */
    private String option3;
	/**
	 * 对应图片ID
	 */
    private Long imageId;
	/**
	 * 
	 */
    private Double weight;
	/**
	 * 计量单位
	 */
    private String weightUnit;
	/**
	 * 
	 */
    private Long adminVariantId;
	/**
	 * 1:可用 0不可用
	 */
    private Integer state;

}
