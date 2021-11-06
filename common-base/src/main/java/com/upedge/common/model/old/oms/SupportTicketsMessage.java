package com.upedge.common.model.old.oms;

import lombok.Data;

import java.util.Date;

/**
 * @author xwCui
 */
@Data
public class SupportTicketsMessage{

	/**
	 * 
	 */
    private Long id;
	/**
	 * 
	 */
    private Long ticketId;
	/**
	 * 消息
	 */
    private String message;
	/**
	 * 发送者ID
	 */
    private String fromUserId;
	/**
	 * 接收者ID
	 */
    private String toUserId;
	/**
	 * 消息发送时间
	 */
    private Date sendTime;
	/**
	 * 消息读取时间
	 */
    private Date readTime;
	/**
	 * 0 消息未读 1消息已读
	 */
    private Integer state;
	/**
	 * 0:app 1:admin
	 */
    private Long source;
	/**
	 * 客户消息回复标记 0:未恢复 1:12小时内回复 2:24小时内回复 3:24外回复
	 */
    private Integer flag;

}
