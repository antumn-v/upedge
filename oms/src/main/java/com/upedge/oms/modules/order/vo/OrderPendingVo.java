package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderPendingVo {


    /**
     *
     */
    private Long id;
    /**
     *
     */
    private Long customerId;
    /**
     *
     */
    private BigDecimal payAmount;
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
     * 支付时美元汇率
     */
    private BigDecimal usdRate;
    /**
     *
     */
    private Date createTime;

    private BigDecimal vatAmount;

    /**
     *
     */
    private Long originalOrderId;
    /**
     *
     */
    private String reason;

    private String managerCode;

    private String customerLoginName;

    private Integer reshipTimes;


}
