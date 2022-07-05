package com.upedge.pms.modules.product.entity;

import lombok.Data;

/**
 * @author gx
 */
@Data
public class ProductAttribute{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 商品id
	 */
    private Long productId;
	/**
	 * 1688目录id
	 */
    private Long aliCnCategoryId;
	/**
	 * 1688目录名称
	 */
    private String aliCnCategoryName;
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
	 * 产品货号
	 */
	private String itemNo;
	/**
	 * 赛盒仓库id
	 */
    private Integer warehouseCode;

}
