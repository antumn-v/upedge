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

	private BigDecimal originalProductAmount;
	/**
	 * 
	 */
    private BigDecimal shipPrice;
	/**
	 * 
	 */
    private BigDecimal amount;

	private BigDecimal originalAmount;
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
    private String purchaseStatus;
	/**
	 * 
	 */
	private Integer editState;

    private Integer purchaseState;
	/**
	 * 0=1688采购
	 */
    private Integer purchaseType;

	private Integer purchaseQuantity;
	/**
	 * 
	 */
    private Long operatorId;
	/**
	 * 
	 */
    private String warehouseCode;
	/**
	 * 留言
	 */
    private String remark;
	/**
	 * 物流单号
	 */
    private String trackingCode;
	/**
	 * 发货时间
	 */
    private Date deliveredTime;
	/**
	 * 收货时间
	 */
    private Date receiveTime;
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

	public PurchaseOrder(Long id, String purchaseId, BigDecimal productAmount, BigDecimal shipPrice, BigDecimal amount, BigDecimal discountAmount, String supplierName,  Integer purchaseState, Integer purchaseType, Long operatorId,Integer purchaseQuantity) {
		this.id = id;
		this.purchaseId = purchaseId;
		this.productAmount = productAmount;
		this.originalProductAmount = productAmount;
		this.shipPrice = shipPrice;
		this.amount = amount;
		this.originalAmount = amount;
		this.discountAmount = discountAmount;
		this.supplierName = supplierName;
		this.purchaseState = purchaseState;
		this.purchaseType = purchaseType;
		this.operatorId = operatorId;
		this.warehouseCode = "CNHZ";
		this.createTime = new Date();
		this.updateTime = new Date();
		this.purchaseQuantity = purchaseQuantity;
		this.editState = 0;
	}
}
