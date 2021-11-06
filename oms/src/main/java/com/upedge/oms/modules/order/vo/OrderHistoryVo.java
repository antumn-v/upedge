package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderHistoryVo {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private Long customerId;

    private String customerLoginName;
    /**
     *
     */
    private Long shipMethodId;

    private Long toAreaId;

    private String toAreaName;

    private String shippingMethodName;

    private Integer saiheTransportId;

    private String saiheTransportName;
    /**
     *
     */
    private BigDecimal totalWeight;
    /**
     *
     */
    private Integer payMethod;
    /**
     *
     */
    private Date payTime;
    /**
     *
     */
    private Long paymentId;
    /**
     *
     */
    private Integer payState;
    /**
     *
     */
    private Integer refundState;
    /**
     *
     */
    private Integer shipState;
    /**
     * 订单类型  正常订单=0 补发订单=1 拆分订单=2 合并订单=3
     */
    private Integer orderType;
    /**
     *
     */
    private Long addressId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

    private String saiheOrderCode;

    private String adminUserId;

    private Integer reshipTimes;
    /**
     *正常 = 0,待审查 = 1,作废 = 2
     */
    private Integer orderStatus;

    private Long originalOrderId;

    /**
     * 店铺OrderNumber
     */
    private String orderNumber;

    private String fulfillmentStatus;


    //回传追踪号
    private String trackingCode;
    //回传运输方式
    private String shipMethodName;
    //回传状态
    private Integer trackState;

    private String managerCode;
}
