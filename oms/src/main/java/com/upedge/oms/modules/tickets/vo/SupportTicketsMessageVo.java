package com.upedge.oms.modules.tickets.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SupportTicketsMessageVo {

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

    private String managerName;
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
    private Integer source;


}
