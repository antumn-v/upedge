package com.upedge.pms.modules.purchase.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class VariantWarehouseStock{

	/**
	 * 
	 */
    private Long variantId;

	private String variantSku;

	private String barcode;
	/**
	 * 仓库代码
	 */
    private String warehouseCode;
	/**
	 * 出库比例
	 */
    private Integer stockScale;
	/**
	 * 安全库存
	 */
    private Integer safeStock;
	/**
	 * 可用库存
	 */
	private Integer availableStock;
	/**
	 * 锁定库存
	 */
    private Integer lockStock;
	/**
	 * 采购库存
	 */
    private Integer purchaseStock;
	/**
	 * 备注
	 */
    private String remark;
	/**
	 * 
	 */
    private String shelfNum;

	private Date updateTime;

	private Integer stockState;

	public VariantWarehouseStock() {
	}

	public VariantWarehouseStock(Long variantId, String warehouseCode, Integer stockScale, Integer availableStock, Integer lockStock, Integer purchaseStock, String remark, String shelfNum) {
		this.variantId = variantId;
		this.warehouseCode = warehouseCode;
		this.stockScale = stockScale;
		this.availableStock = availableStock;
		this.lockStock = lockStock;
		this.purchaseStock = purchaseStock;
		this.remark = remark;
		this.shelfNum = shelfNum;
		this.updateTime = new Date();
	}
}
