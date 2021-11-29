package com.upedge.oms.modules.order.vo;

import lombok.Data;

@Data
public class OrderManageQuery {

    /**
     *
     */
    private Integer payState;
    /**
     *退款状态
     */
    private Integer refundState;
    /**
     *
     */
    private Integer shipState;

    private Integer quoteState;
    /**
     *
     */
    private Long id;
    /**
     *交易号
     */
    private Long paymentId;
    /**
     *客户id
     */
    private Long customerId;
    /**
     * 运输单号
     */
    private String trackingCode;

    private String saiheOrderCode;
    /**
     * 店铺OrderNumber
     */
    private String orderNumber;
    /**
     * 订单类型  正常订单=0 补发订单=1 拆分订单=2 合并订单=3
     */
    private Integer orderType;
    /**
     *正常 = 0,待审查 = 1,作废 = 2
     */
    private Integer orderStatus;
    /**
     * 店铺id
     */
    private Long storeId;

    private Long originalOrderId;

    /**
     * saiheTrackState
     *赛盒回传追踪号状态
     */
    private Integer saiheTrackState;

    /**
     *赛盒回传订单状态
     */
    private Integer trackState;

    /**
     * 店铺状态
     */
    private Integer financialStatus;

    /**
     * 补发来源订单id:
     */
    private String source;


    /**
     * storeState
     * 店铺追踪号上传状态
     */
    private Integer storeState;

    /**
     * fulfillmentStatus
     * 0=未发货 1=部分发货 2=全部发货
     */
    private Integer fulfillmentStatus;

    private String managerCode;

}
