package com.upedge.oms.modules.order.vo;

import com.upedge.oms.modules.order.entity.OrderRefundItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderRefundVo {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private String reason;
    /**
     *
     */
    private Long orderId;
    /**
     * 商品金额
     */
    private BigDecimal productAmount;

    private BigDecimal cnyProductAmount;
    /**
     * 产品抵扣金额
     */
    private BigDecimal productDischargeAmount;
    /**
     * 运费
     */
    private BigDecimal shipPrice;
    /**
     * vat
     */
    private BigDecimal vatAmount;

    private BigDecimal serviceFee;
    /**
     * paypal手续费
     */
    private BigDecimal fixFee;
    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    private Integer payMethod;
    /**
     *
     */
    private Long customerId;
    private String customerLoginName;
    private String customerName;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     * 驳回理由
     */
    private String rejectInfo;
    /**
     *
     */
    private String managerCode;
    /**
     *可退最大金额
     */
    private BigDecimal maxRefundAmount;

    private BigDecimal refundAmount;
    /**
     *
     */
    private BigDecimal refundVatAmount;
    /**
     *
     */
    private BigDecimal refundProductAmount;
    /**
     *
     */
    private BigDecimal refundShippingPrice;
    /**
     * 0:申请中 1:确认退款 2:已驳回
     */
    private Integer state;
    /**
     * 0:app,1:admin
     */
    private Integer source;
    /**
     * 退款订单发货状态 0:未发货 1:已发货
     */
    private Integer trackingState;
    /**
     * 赛盒订单渠道
     */
    private Integer orderSourceId;
    /**
     * 备注
     */
    private String remark;

    /**
     * 0:未发货 1:已发货 2:已完成
     */
    private Integer shipState;

    /**
     * 赛盒订单号
     */
    private Long saiheOrderCode;

    /**
     * 交易号
     */
    private Long paymentId;
    private String referrerLoginName;

    private List<OrderRefundItem> orderRefundItemList;

    //
    private String customerAffiliateName;
    private Integer saiheOrderState;
    private Integer saiheorderStatus;

    public void initMaxRefundAmount(){
        this.maxRefundAmount = this.vatAmount.add(this.productAmount).add(this.shipPrice).add(this.serviceFee).subtract(this.productDischargeAmount);
    }

}
