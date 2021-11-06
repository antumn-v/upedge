package com.upedge.oms.modules.wholesale.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WholesaleOrderVo {

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
    private BigDecimal vatAmount;
    /**
     *
     */
    private BigDecimal productDischargeAmount;
    /**
     *
     */
    private BigDecimal fixFee;
    /**
     * 0=balance,1=paypal
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
     * -1=已取消。0=未支付。1=已支付。2=退款申请中。3=部分退款。4=全部退款  5=支付中
     */
    private Integer payState;
    /**
     *
     */
    private Integer refundState;
    /**
     * 0=未发货。1=已发货。
     */
    private Integer shipState;
    /**
     *
     */
    private Long toAreaId;
    /**
     *
     */
    private String saiheOrderCode;
    /**
     *   正常 = 0,待审查 = 1,作废 = 2
     */
    private Integer orderStatus;
    /**
     * 0=普通订单，1=excel导入
     */
    private Integer orderType;

    private String toAreaName;

    private String shippingMethodName;

    private Integer saiheTransportId;

    private String saiheTransportName;

    private Integer bagFlag;

    //回传追踪号
    private String trackingCode;
    //回传运输方式
    private String shipMethodName;

    private String managerCode;

    private Integer reshipTimes;

    private Long originalOrderId;
    private String managerName;
    private String customerLoginName;


}
