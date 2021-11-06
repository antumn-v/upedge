package com.upedge.common.model.old.pms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppProductAttribute{

	/**
	 * 
	 */
    private Long appProductId;
	/**
	 * 原商品ID
	 */
    private Long adminProductId;
	/**
	 * 商品名称
	 */
    private String productTitle;
	/**
	 * 产品类别
	 */
    private String type;
	/**
	 * 运输模板ID
	 */
    private Long shippingId;
	/**
	 * 产品在product选项卡展示的图片
	 */
    private String productImage;
	/**
	 * 产品状态（已上架，未上架）
	 */
    private Integer productStatus;
	/**
	 * 产品标签
	 */
    private String tags;
	/**
	 * 商品上架店铺后的ID
	 */
    private Long storeProductId;
	/**
	 * 
	 */
    private String originalProductId;
	/**
	 * 供应商名称
	 */
    private String supplierName;
	/**
	 * 供应商ID
	 */
    private String supplierId;
	/**
	 * 
	 */
    private Long collectId;
	/**
	 * 产品来源
	 */
    private String source;
	/**
	 * 产品主图是否可用 1：可用 ， 0 ：不可用
	 */
    private Integer imageState;
	/**
	 * 原标题
	 */
    private String originalTitle;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private Long storeId;

}
