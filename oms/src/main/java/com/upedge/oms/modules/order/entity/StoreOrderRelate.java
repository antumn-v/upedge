package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreOrderRelate{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long storeOrderId;
	/**
	 * 
	 */
    private Long orderId;
	/**
	 * 
	 */
    private String platOrderName;
	/**
	 * 
	 */
    private Integer financialStatus;
	/**
	 * 
	 */
    private Integer fulfillmentStatus;
	/**
	 * 
	 */
    private Date platOrderCreateTime;
	/**
	 * 
	 */
    private Date orderCreateTime;

    private String storeName;

    private String orderCustomerName;

	public StoreOrderRelate(StoreOrder storeOrder) {
		this.financialStatus = storeOrder.getFinancialStatus();
		this.fulfillmentStatus = storeOrder.getFulfillmentStatus();
		this.platOrderCreateTime = storeOrder.getCreateTime();
		this.storeOrderId = storeOrder.getId();
		this.platOrderName = storeOrder.getPlatOrderName();
		this.storeName = storeOrder.getStoreName();
	}

	public StoreOrderRelate() {
	}
}
