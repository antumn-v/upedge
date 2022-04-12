package com.upedge.sms.modules.overseaWarehouse.entity;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

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

}
