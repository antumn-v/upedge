package com.upedge.oms.modules.order.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderActionLog{

	/**
	 * 
	 */
    private Integer id;
	/**
	 * 
	 */
    private Long storeOrderItemId;
	/**
	 * 
	 */
    private Long oldOrderId;
	/**
	 * 
	 */
    private Long oldItemId;
	/**
	 * 
	 */
    private Integer oldQuantity;
	/**
	 * 
	 */
    private Long newOrderId;
	/**
	 * 
	 */
    private Long newItemId;
	/**
	 * 
	 */
    private Integer newQuantity;
	/**
	 * 1:合并普通订单，2：合并已拆分订单，3：拆分普通订单，4：拆分已合并订单
	 */
    private Integer actionType;
	/**
	 * 
	 */
    private Date createTime;

}
