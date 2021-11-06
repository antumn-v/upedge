package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class StoreOrderRefundItem{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long storeRefundId;
	/**
	 * 
	 */
    private Long storeItemId;
	/**
	 * 
	 */
    private Long storeOrderId;
	/**
	 * 
	 */
    private Integer refundQuantity;
	/**
	 * 
	 */
    private BigDecimal refundAmount;
	/**
	 * 
	 */
    private BigDecimal usdPrice;
	/**
	 * 
	 */
    private Date createTime;

}
