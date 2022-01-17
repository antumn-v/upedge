package com.upedge.oms.modules.order.vo;

import com.upedge.oms.modules.order.entity.OrderAddress;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AppOrderVo {

    private Long id;
    /**
     *
     */
    private Long customerId;
    /**
     *
     */
    private Long storeId;

    private String storeName;

    private String storeUrl;

    private String orderCustomerName;
    /**
     *
     */
    private BigDecimal payAmount;
    /**
     *
     */

    private Long shipMethodId;

    private String shipMethodName;

    private String trackingCode;

    private BigDecimal shipPrice;

    private BigDecimal serviceFee;
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
    private BigDecimal productDischargeAmount = BigDecimal.ZERO;
    /**
     *
     */
    private BigDecimal fixFee = BigDecimal.ZERO;
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

    private Integer quoteState;
    /**
     * 订单类型  正常订单=0 补发订单=1 拆分订单=2 合并订单=3
     */
    private Integer orderType;

    private boolean hasPayFailed;

    private Long toAreaId;

    private String country;

    private String note;

    private Date createTime;

    private BigDecimal vatAmount = BigDecimal.ZERO;

    private List<AppStoreOrderVo> storeOrderVos;

    private OrderAddress orderAddress;

}
