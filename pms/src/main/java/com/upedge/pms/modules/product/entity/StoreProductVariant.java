package com.upedge.pms.modules.product.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreProductVariant{

	/**
	 * 变体ID
	 */
private Long id;
	/**
	 * 变体对应的产品ID
	 */
private Long productId;
	/**
	 * 店铺平台变体ID
	 */
private String platVariantId;
	/**
	 * 店铺平台产品ID
	 */
private String platProductId;
	/**
	 * 
	 */
private String title;
	/**
	 * 售价
	 */
private BigDecimal price;
	/**
	 * 
	 */
private String sku;
	/**
	 * 图片
	 */
private String image;
	/**
	 * 
	 */
private Long adminVariantId;
	/**
	 * 1:可用 0不可用
	 */
private Integer state;
	/**
	 * 导入系统时间
	 */
private Date importTime;

}
