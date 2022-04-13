package com.upedge.sms.modules.overseaWarehouse.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gx
 */
@Data
public class OverseaWarehouseServiceOrder{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long customerId;
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
    private Integer shipType;
	/**
	 * 
	 */
    private BigDecimal payAmount;
	/**
	 * 
	 */
    private Integer payState;
	/**
	 * 
	 */
    private Integer shipState;
	/**
	 * 
	 */
    private Integer refundState;
	/**
	 * 
	 */
    private Long paymentId;
	/**
	 * 
	 */
    private String warehouseCode;
	/**
	 * 
	 */
    private Long warehouseOrderId;
	/**
	 * 
	 */
    private Integer warehouseOrderState;
	/**
	 * 
	 */
    private String trackingCode;
	/**
	 * 
	 */
    private Date payTime;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 
	 */
    private Long managerId;

	public OverseaWarehouseServiceOrder() {
		this.payState = 0;
		this.refundState = 0;
		this.shipState = 0;
		this.createTime = new Date();
		this.updateTime = createTime;
	}
}
