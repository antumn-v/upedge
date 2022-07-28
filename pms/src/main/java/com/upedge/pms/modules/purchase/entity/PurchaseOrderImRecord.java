package com.upedge.pms.modules.purchase.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author gx
 */
@Data
public class PurchaseOrderImRecord{

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
    private String trackingCode;
	/**
	 * 
	 */
    private Long operatorId;
	/**
	 * 
	 */
    private Date createTime;

}
