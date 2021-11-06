package com.upedge.oms.modules.tickets.request;

import com.upedge.oms.modules.tickets.entity.SupportTickets;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class SupportTicketsAddRequest{

    /**
    * 潘达订单ID
    */
    private Long orderId;
    /**
    * 用户ID
    */
    private Long customerId;
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
    * 管理员ID
    */
    private String managerCode;

    public SupportTickets toSupportTickets(){
        SupportTickets supportTickets=new SupportTickets();
        supportTickets.setOrderId(orderId);
        supportTickets.setCustomerId(customerId);
        supportTickets.setState(state);
        supportTickets.setResult(result);
        supportTickets.setDescribes(describes);
        supportTickets.setCreateTime(createTime);
        supportTickets.setFinishTime(finishTime);
        supportTickets.setManagerCode(managerCode);
        return supportTickets;
    }

}
