package com.upedge.pms.modules.purchase.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class PurchaseOrderTracking{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long purchaseOrderId;
	/**
	 * 
	 */
    private String purchaseId;
	/**
	 * 
	 */
    private String trackingCode;
	/**
	 * 
	 */
    private String trackingCompany;
	/**
	 * 
	 */
    private Date updateTime;

	private Date receiveTime;

	private String latestInfo;

	public PurchaseOrderTracking() {
	}

	public PurchaseOrderTracking(Long purchaseOrderId, String purchaseId, String trackingCode, String trackingCompany) {
		this.purchaseOrderId = purchaseOrderId;
		this.purchaseId = purchaseId;
		this.trackingCode = trackingCode;
		this.trackingCompany = trackingCompany;
		this.updateTime = new Date();
	}
}
