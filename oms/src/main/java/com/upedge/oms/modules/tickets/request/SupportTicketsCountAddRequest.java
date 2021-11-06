package com.upedge.oms.modules.tickets.request;

import com.upedge.oms.modules.tickets.entity.SupportTicketsCount;
import lombok.Data;

/**
 * @author author
 */
@Data
public class SupportTicketsCountAddRequest{

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

    public SupportTicketsCount toSupportTicketsCount(){
        SupportTicketsCount supportTicketsCount=new SupportTicketsCount();
        supportTicketsCount.setMessageAll(messageAll);
        supportTicketsCount.setANum(aNum);
        supportTicketsCount.setBNum(bNum);
        supportTicketsCount.setTicketId(ticketId);
        supportTicketsCount.setCustomerId(customerId);
        return supportTicketsCount;
    }

}
