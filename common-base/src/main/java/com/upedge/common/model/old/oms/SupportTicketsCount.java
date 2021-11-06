package com.upedge.common.model.old.oms;

import lombok.Data;

/**
 * @author xwCui
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
    private Long appUserId;

}
