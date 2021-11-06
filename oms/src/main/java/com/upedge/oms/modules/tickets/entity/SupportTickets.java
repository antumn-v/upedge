package com.upedge.oms.modules.tickets.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class SupportTickets{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 潘达订单ID
	 */
    private Long orderId;
	/**
	 * 用户ID
	 */
    private Long customerId;

    private String customerName;
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
	/**
	 * 
	 */
    private Integer timesCount;

    private Integer lastSource;

    private String managerCode;

    private String managerName;

    private Long managerCustomerId;

}
