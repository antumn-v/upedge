package com.upedge.oms.modules.wholesale.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class WholesaleOrderAppVo {

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
    private BigDecimal shipPrice;

    private BigDecimal serviceFee;

    private Integer freightReview;

    private String shipName;

    private String shipMethodName;

    private String trackingCode;
    /**
     *
     */
    private BigDecimal totalWeight;
    private BigDecimal volumeWeight;
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
    private Date createTime;
    /**
     * 0=普通订单，1=excel导入
     */
    private Integer orderType;

    private String customerOrderNumber;

    private String storeName;

    private List<WholesaleOrderItemVo> itemVos;
}
