package com.upedge.ums.modules.account.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AccountLog {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private Long accountId;


    private Long customerId;

    private Integer transactionType;

    private Integer orderType;

    private Integer payMethod;
    /**
     *
     */
    private Long transactionId;
    /**
     *
     */
    private BigDecimal balance;
    /**
     *
     */
    private BigDecimal affiliateRebate;

    private BigDecimal vipRebate;
    /**
     *
     */
    private BigDecimal credit;
    /**
     * 手续费
     */
    private BigDecimal fixFee;
    /**
     *
     */
    private Date createTime;

    /**
     * 开始时间 搜索使用  不存在数据库映射
     */
    private String shipDateStart;


    /**
     * 结束时间   搜索使用  不存在数据库映射
     */
    private String shipDateEnd;

    public AccountLog() {
    }

    public AccountLog(Long accountId, Long customerId, Integer transactionType, Integer orderType, Integer payMethod, Long transactionId, BigDecimal balance, BigDecimal affiliateRebate, BigDecimal vipRebate, BigDecimal credit, BigDecimal fixFee, Date createTime) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.transactionType = transactionType;
        this.orderType = orderType;
        this.payMethod = payMethod;
        this.transactionId = transactionId;
        this.balance = balance;
        this.affiliateRebate = affiliateRebate;
        this.vipRebate = vipRebate;
        this.credit = credit;
        this.fixFee = fixFee;
        this.createTime = createTime;
    }
}
