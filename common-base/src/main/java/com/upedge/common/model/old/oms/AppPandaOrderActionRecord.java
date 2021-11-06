package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class AppPandaOrderActionRecord{

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
