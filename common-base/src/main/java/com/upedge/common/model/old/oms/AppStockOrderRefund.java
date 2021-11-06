package com.upedge.common.model.old.oms;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppStockOrderRefund{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 备库订单ID
	 */
    private Long orderId;
	/**
	 * 退款总金额
	 */
    private BigDecimal amount;
	/**
	 * 退款状态，申请中=0，通过=1，驳回=2
	 */
    private Integer state;
	/**
	 * 退款原因
	 */
    private String reason;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;
	/**
	 * 0:app,1:admin
	 */
    private Integer source;
	/**
	 * 
	 */
    private Long appUserId;
	/**
	 * 
	 */
    private String adminUserId;
	/**
	 * 
	 */
    private Integer warehouseId;

}
