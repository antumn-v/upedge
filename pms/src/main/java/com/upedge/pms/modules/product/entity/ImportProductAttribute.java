package com.upedge.pms.modules.product.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @author gx
 */
@Data
public class ImportProductAttribute{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 原商品ID
	 */
    private String sourceProductId;
	/**
	 * 商品名称
	 */
    private String title;
	/**
	 * 产品类别
	 */
    private String type;
	/**
	 * 产品在product选项卡展示的图片
	 */
    private String image;
	/**
	 * 产品状态（1=已上架，0=未上架,-1已删除）
	 */
    private Integer state;
	/**
	 * 产品标签
	 */
    private String tags;
	/**
	 * 产品来源,0=app,1=ali
	 */
    private Integer source;
	/**
	 * 
	 */
    private String supplierName;
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
    private Long customerId;

}
