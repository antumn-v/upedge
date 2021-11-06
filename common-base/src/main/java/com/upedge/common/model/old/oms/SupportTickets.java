package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class SupportTickets{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 店铺订单ID
	 */
    private Long appOrderId;
	/**
	 * 潘达订单ID
	 */
    private Long upedgeOrderId;
	/**
	 * 用户ID
	 */
    private Long appUserId;
	/**
	 * 管理员ID
	 */
    private String adminUserId;
	/**
	 * 店铺ID
	 */
    private Long storeId;
	/**
	 * 0:prossing  1:solved
	 */
    private Integer state;
	/**
	 * 处理结果
	 */
    private String result;
	/**
	 * 问题描述
	 */
    private String describes;
	/**
	 * 开始时间
	 */
    private Date createTime;
	/**
	 * 结束时间
	 */
    private Date finishTime;

}
