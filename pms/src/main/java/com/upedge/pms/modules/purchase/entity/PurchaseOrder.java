package com.upedge.pms.modules.purchase.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class PurchaseOrder{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private String purchaseId;
	/**
	 * 
	 */
    private BigDecimal productAmount;
	/**
	 * 
	 */
    private BigDecimal shipPrice;
	/**
	 * 
	 */
    private BigDecimal amount;
	/**
	 * 
	 */
    private BigDecimal discountAmount;
	/**
	 * 
	 */
    private String supplierName;
	/**
	 * 
	 */
    private Integer purchaseState;
	/**
	 * 0=1688采购
	 */
    private Integer purchaseType;
	/**
	 * 
	 */
    private Long operatorId;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;


	public PurchaseOrder() {
	}

	public PurchaseOrder(Long id, String purchaseId, BigDecimal productAmount, BigDecimal shipPrice, BigDecimal amount, BigDecimal discountAmount, String supplierName, Integer purchaseState, Integer purchaseType, Long operatorId) {
		this.id = id;
		this.purchaseId = purchaseId;
		this.productAmount = productAmount;
		this.shipPrice = shipPrice;
		this.amount = amount;
		this.discountAmount = discountAmount;
		this.supplierName = supplierName;
		this.purchaseState = purchaseState;
		this.purchaseType = purchaseType;
		this.operatorId = operatorId;
		this.createTime = new Date();
		this.updateTime = new Date();
	}
}
