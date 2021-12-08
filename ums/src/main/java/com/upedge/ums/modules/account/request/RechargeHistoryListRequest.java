package com.upedge.ums.modules.account.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.account.entity.RechargeRequestLog;
import lombok.Data;

import java.math.BigDecimal;


/**
 * admin  充值记录 Query
 */
@Data
public class RechargeHistoryListRequest extends Page<RechargeRequestLog> {

    /**
     * 充值id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 状态  recharge_status  0 未使用 1 已使用 2已完成 3已撤销
     */
    private Integer rechargeStatus;

    /**
     * 方式： recharge_type
     * 0:电汇 1:paypal充值 2:payoneer充值,3:payoneer申请
     */
    private Integer rechargeType;

    /**
     * 充值金额 amount      到账金额
     */
    private BigDecimal amount;

    /**
     * 创建开始时间
     */
    private String startDay;

    /**
     * 创建结束时间
     */
    private String endDay;

    /**
     * 银行流水号
     */
    private String transferFlow;
}
