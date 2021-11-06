package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class UserLockStockRecord{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long userId;
	/**
	 * 
	 */
    private Long adminVariantId;
	/**
	 * 
	 */
    private Integer lockStockQuantity;
	/**
	 * 
	 */
    private Long upedgeOrderId;
	/**
	 * 
	 */
    private Long paymentId;
	/**
	 * 0=锁定中，1=已扣除，-1=回滚
	 */
    private Integer lockState;
	/**
	 * 0=普通订单，1=批发订单
	 */
    private Integer orderType;
	/**
	 * 
	 */
    private Date createTime;
	/**
	 * 
	 */
    private Date updateTime;

}
