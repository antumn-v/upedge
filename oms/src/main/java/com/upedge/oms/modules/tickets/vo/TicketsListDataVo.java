package com.upedge.oms.modules.tickets.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TicketsListDataVo {

    /**
     * 客户消息总数
     */
    private Integer messageAll;
    /**
     * 12h未回客户消息数
     */
    private Integer anum;
    /**
     * 24h未回客户消息数
     */
    private Integer bnum;
    /**
     * 12h未回客户消息数占比
     */
    private BigDecimal aRate;
    /**
     * 24h未回客户消息数占比
     */
    private BigDecimal bRate;
    /**
     * ticket_id
     */
    private String ticketId;
    /**
     * 客户id
     */
    private String customerId;
    /**
     * userManager
     */
    private String managerCode;

}
