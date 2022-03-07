package com.upedge.oms.modules.stock.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class AdminStockOrderVo {

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
     * 客户经理id
     */
    private String managerCode;
    /**
     * 支付方式,balance = 0,paypal=1
     */
    private Integer payMethod;
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
    /**
     * 退款状态:0=未退款，1=申请中，2=驳回，3=部分退款，4=全部退款
     */
    private Integer refundState;
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
     * 后台状态 0:未导入赛盒 1:已导入赛盒
     */
    private Integer saiheState;
    /**
     * 处理人
     */
    private String adminUserId;
    /**
     * 赛盒备库订单号
     */
    private String saiheCode;

    private List<AdminStockOrderItemVo> items;

}
