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
    private Long shipMethodId;
    /**
     *
     */
    private BigDecimal shipPrice = BigDecimal.ZERO;

    private String shipName;
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
    private BigDecimal vatAmount = BigDecimal.ZERO;
    /**
     *
     */
    private BigDecimal productDischargeAmount = BigDecimal.ZERO;
    /**
     *
     */
    private BigDecimal fixFee = BigDecimal.ZERO;
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
    private Integer payState = 0;
    /**
     *
     */
    private Integer refundState = 0;
    /**
     * 0=未发货。1=已发货。
     */
    private Integer shipState = 0;
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
    private Integer orderType = 0;

    private String customerOrderNumber;

    private String storeName;

    private List<WholesaleOrderItemVo> itemVos;
}
