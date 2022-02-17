package com.upedge.pms.modules.inventory.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Ï¦Îí
 */
@Data
public class ProductSaiheInventory{

	/**
	 * 
	 */
    private String variantSku;
	/**
	 * 可用库存数量
	 */
    private Integer goodNum;
	/**
	 * 锁定库存数量
	 */
    private Integer lockNum;
	/**
	 * 仓库id
	 */
    private Integer warehouseCode;
	/**
	 * 库存更新时间
	 */
    private Date updateTime;
	/**
	 * 活跃天数(库龄)
	 */
    private Integer activeDays;
	/**
	 * 活跃时间
	 */
    private Date activeTime;
	/**
	 * 库位
	 */
    private String positionCode;
	/**
	 * 采购中
	 */
    private Integer processingNum;
	/**
	 * 历史入库
	 */
    private Integer historyInNum;
	/**
	 * 历史出库
	 */
    private Integer historyOutNum;
	/**
	 * 客户库存总数
	 */
    private Integer customerStock;

}
