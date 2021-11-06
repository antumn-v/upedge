package com.upedge.ums.modules.affiliate.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AdminWithdrawalVo {


    /**
     *
     */
    private String username;
    /**
     *
     */
    private String loginName;

    /**
     *
     */
    private Long id;
    /**
     * 申请人
     */
    private Long customerId;

    /**
     * 申请提现账户
     */
    private Long withdrawalAccountId;
    /**
     * 提现金额
     */
    private BigDecimal amount;
    /**
     * PayPal,Payoneer,SourcinBox
     */
    private Integer path;
    /**
     * 备注
     */
    private String remark;
    /**
     * 申请中=0；申请通过=1，拒绝申请=2
     */
    private Integer state;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     * 拒绝原因
     */
    private String reason;
    /**
     *
     */
    private String managerCode;
    /**
     * 收款账户
     */
    private String receiveAccount;
    /**
     * 付款账号
     */
    private String paymentAccount;

    private Integer gteState;

}
