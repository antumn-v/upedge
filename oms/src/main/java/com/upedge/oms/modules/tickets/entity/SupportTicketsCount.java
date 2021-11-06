package com.upedge.oms.modules.tickets.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class SupportTicketsCount{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 客户消息总数
	 */
    private Integer messageAll;
	/**
	 * 12h未回客户消息数
	 */
    private Integer aNum;
	/**
	 * 24h未回客户消息数
	 */
    private Integer bNum;
	/**
	 * 
	 */
    private Long ticketId;
	/**
	 * 客户id
	 */
    private Long customerId;

    private String managerCode;

    private Date createTime;
}
