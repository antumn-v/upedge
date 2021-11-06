package com.upedge.common.model.old.pms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AdminProductAttribute{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 商品id
	 */
    private Long productId;
	/**
	 * 商品sku
	 */
    private String productSku;
	/**
	 * 商品标题
	 */
    private String productTitle;
	/**
	 * 商品主图
	 */
    private String productImage;
	/**
	 * 运输模板id
	 */
    private Long shippingId;
	/**
	 * 物流属性id
	 */
    private Long shippingAttributeId;
	/**
	 * 折扣 0-1不包含0
	 */
    private BigDecimal discount;
	/**
	 * 1688目录id
	 */
    private Long aliCnCategoryId;
	/**
	 * 产品货号（sku导出会使用）
	 */
    private String itemNo;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Long supplierId;
	/**
	 * 供应商名称
	 */
    private String supplierName;
	/**
	 * 1688上30天成交量
	 */
    private Integer turnover;
	/**
	 * 1688上的评分
	 */
    private Integer score;
	/**
	 * 报关英文名
	 */
    private String entryEname;
	/**
	 * 报关中文名
	 */
    private String entryCname;
	/**
	 * 赛盒仓库id
	 */
    private Integer warehouseId;
	/**
	 * 采购收货备注
	 */
    private String receiptRemark;
	/**
	 * 质检备注  
	 */
    private String procurementCheckMemo;
	/**
	 * 发货打包备注
	 */
    private String deliveryPackNote;

}
