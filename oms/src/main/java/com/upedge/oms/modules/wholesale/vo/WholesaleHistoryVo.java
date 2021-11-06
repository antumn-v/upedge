package com.upedge.oms.modules.wholesale.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class WholesaleHistoryVo {

    private String managerCode;

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
    /**
     *
     */
    private BigDecimal totalWeight;
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

    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
}
