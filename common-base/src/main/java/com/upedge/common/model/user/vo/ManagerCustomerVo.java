package com.upedge.common.model.user.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户经理客户信息
 */
@Data
public class ManagerCustomerVo extends UserInfoVo {

    private String managerCode;

    private Integer customerInfoState;

    private String remark;

    private Boolean isAuthStore;

    private Date lastLoginTime;

    private Integer loginCount;

    /**
     * 状态  0 正常 1 未激活 2 锁定 3注销
     */
    private Integer cstatus;

    private String customerLoginName;

    /**
     * balance 余额
     * rebate返利
     * credit 已使用的信用额度
     * creditLimit 可使用的信用额度
     * commission 佣金
     */
    private BigDecimal balance;
    private BigDecimal rebate;
    private BigDecimal credit;
    private BigDecimal creditLimit;
    private BigDecimal commission;

    /**
     *  订单总金额  orderAllMoney
     *   订单总数 totalupedgePaidOrderNum;
     *  取消订单数  cancelOrderCount
     *   支付且发货的订单数  PayAndShipOrderCount
     *   已支付未发货订单数  PayButNoShipOrderCount
     *   已完成订单数    completeOrderCount
     *   待支付订单数  noPayOrderCount
     */
    private BigDecimal orderAllMoney;
    private Integer totalupedgePaidOrderNum;
    private Integer cancelOrderCount;
    private Integer PayAndShipOrderCount;
    private Integer PayButNoShipOrderCount;
    private Integer completeOrderCount;
    private Integer noPayOrderCount;
}
