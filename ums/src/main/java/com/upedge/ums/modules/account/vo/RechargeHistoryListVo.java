package com.upedge.ums.modules.account.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值历史分页回参vo
 */
@Data
public class RechargeHistoryListVo {

    /**
     * 充值id
     */
    private Long id;

    /**
     * account_id
     */
    private Long accountId;

    /**
     * accountPaymethodId
     */
    private Long accountPaymethodId;

    /**
     * 用户id
     */
    private Long customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * handle_user_id 审批人
     */
    private Long handleUserId;

    /**
     *  审批人名称
     */
    private String handleUserName;

    /**
     *  request_user_id 客户的用户id
     */
    private Long requestUserId;

    /**
     * customerLoginName 用户账号
     */
    private String customerLoginName;



    /**
     * repayment_amount 还款金额
     */
    private BigDecimal repaymentAmount;

    /**
     * 充值金额 amount      到账金额
     */
    private BigDecimal amount;

    /**
     * customer_money 申请金额
     */
    private BigDecimal customerMoney;

    /**
     * benefits 返点
     */
    private BigDecimal benefits;

    /**
     * remarks 备注
     */
    private String remarks;

    /**
     * STATUS  0=审核中，1=成功，2=失败  3=payoneer待提交付款请求
     */
    private Integer status;

    @DateTimeFormat(pattern = " yyyy-MM-dd HH:mm:ss")
    @JSONField(format = " yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = " yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @DateTimeFormat(pattern = " yyyy-MM-dd HH:mm:ss ")
    @JSONField(format = " yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = " yyyy-MM-dd HH:mm:ss")
    private Date updateTime;



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
     * 银行流水号
     */
    private String transferFlow;

    /**
     * rejectReason
     */
    private String rejectReason;

    /**
     * image
     */
    private String image;

    /**
     * paymentId
     */
    private String paymentId;

    /**
     * receivingAccount 充值信息（更新的到账账号）
     */
    private String receivingAccount;

}
