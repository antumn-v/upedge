package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderVo {


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
    private BigDecimal payAmount;
    /**
     *
     */
    private Long shipMethodId;
    /**
     *
     */
    private BigDecimal shipPrice;
    /**
     *
     */
    private BigDecimal totalWeight;
    /**
     *
     */
    private BigDecimal productAmount;
    /**
     *
     */
    private BigDecimal productDischargeAmount;
    /**
     *
     */
    private BigDecimal fixFee;
    /**
     * 手续费百分比
     */
    private BigDecimal fixFeePercentage;

    private Long toAreaId;

    private String toAreaName;

    private String shippingMethodName;

    private Integer saiheTransportId;

    private String saiheTransportName;
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
     *正常 = 0,待审查 = 1,作废 = 2
     */
    private Integer orderStatus;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

    private String saiheOrderCode;

    private BigDecimal vatAmount;

    private String managerCode;
    private String managerName;


    //回传追踪号
    private String trackingCode;
    //回传运输方式
    private String shipMethodName;
    //回传状态
    private Integer trackState;

    private Integer reshipTimes;

    private Long originalOrderId;

    /**
     * 店铺OrderNumber
     */
    private String orderNumber;


    /**
     * 0=未发货 1=部分发货 2=全部发货
     */
    private String fulfillmentStatus;

    /**
     * 0=已支付 1=部分退款 2=全部退款
     * FinancialStatus
     */
    private Integer financialStatus;
}
