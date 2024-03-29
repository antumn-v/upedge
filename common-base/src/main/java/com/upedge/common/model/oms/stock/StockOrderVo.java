package com.upedge.common.model.oms.stock;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class StockOrderVo {

    /**
     *
     */
    private Long id;
    /**
     * 仓库ID
     */
    private String warehouseCode;
    /**
     * 用户ID
     */
    private Long customerId;
    /**
     * 支付方式,balance = 0,paypal=1
     */
    private Integer payMethod;

    private BigDecimal shipPrice;

    private String shipMethod;
    /**
     * 交易ID
     */
    private Long paymentId;
    /**
     * 交易金额
     */
    private BigDecimal amount;
    /**
     * paypal手续费
     */
    private BigDecimal paypalFee;
    /**
     * 支付状态,待支付=0，已支付=1，取消订单=-1
     */
    private Integer payState;

    private BigDecimal productAmount;
    /**
     * 退款状态:0=未退款，1=申请中，2=驳回，3=部分退款，4=全部退款
     */
    private Integer refundState;

    private Integer shipReview;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;

    /**
     * 采购状态，0=未采购，1=采购中，2=部分采购，3=采购完成
     */
    private Integer purchaseState;

    private List<StockOrderItemVo> items;

}
