package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppPandaOrderProfit{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long storeId;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private Long upedgeOrderId;
	/**
	 * 
	 */
    private BigDecimal storeOrderItemAmount;
	/**
	 * 
	 */
    private BigDecimal storeOrderOtherFee;
	/**
	 * 
	 */
    private BigDecimal storeRefundAmount;
	/**
	 * 
	 */
    private BigDecimal upedgeRefundAmount;
	/**
	 * 
	 */
    private BigDecimal upedgeOrderAmount;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
