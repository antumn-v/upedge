package com.upedge.oms.modules.tickets.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerTicketVo {

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

    private String managerName;

    private String storeName;
    /**
     * 开始时间
     */
    private Date createTime;

    private double processTime;

}
