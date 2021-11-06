package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreOrderOtherFeeRecord{

	/**
	 * 
	 */
    private Long storeOrderId;
	/**
	 * 
	 */
    private BigDecimal storeOrderOtherFee;
	/**
	 * 
	 */
    private Long upedgeOrderId;
	/**
	 * 
	 */
    private Date createTime;

}
