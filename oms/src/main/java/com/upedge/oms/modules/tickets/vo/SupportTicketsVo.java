package com.upedge.oms.modules.tickets.vo;

import com.upedge.oms.modules.order.vo.AppOrderVo;
import lombok.Data;

import java.util.Date;

@Data
public class SupportTicketsVo {

    /**
     *
     */
    private Long id;
    /**
     * 潘达订单ID
     */
    private Long orderId;

    private String orderNumber;
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
     *
     */
    private Integer timesCount;

    private Integer lastSource;

    private Integer percentDay;//处理时效天数

    private Integer msgCount;
    private String managerCode;
    private String customerLoginName;
    private String customerName;

    private AppOrderVo orderVo;


}
