package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
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
