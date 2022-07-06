package com.upedge.pms.modules.product.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class VariantSkuUpdateLog{

	/**
	 * 
	 */
    private Long variantId;
	/**
	 * 
	 */
    private String variantSku;
	/**
	 * 
	 */
    private Date updateTime;

	private Long operatorId;

	public VariantSkuUpdateLog() {
	}

	public VariantSkuUpdateLog(Long variantId, String variantSku,  Long operatorId) {
		this.variantId = variantId;
		this.variantSku = variantSku;
		this.updateTime = new Date();
		this.operatorId = operatorId;
	}
}
