package com.upedge.ums.modules.account.entity;

import com.upedge.common.model.old.ums.AppRecharge;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 海桐
 */
@Data
public class RechargeRequestLog {
    private Long id;

    private Long accountId;

    private Integer accountPaymethodId;

    private Long customerId;

    private Long requestUserId;

    private Long handleUserId;

    //到账金额 默认=客户申请金额
    private BigDecimal amount;

    private BigDecimal benefits = BigDecimal.ZERO;

    private BigDecimal repaymentAmount = BigDecimal.ZERO;

    //客户申请金额
    private BigDecimal customerMoney;

    private String remarks;

    private String transferFlow;

    private Integer status;

    private Integer gteStatus;

    private Integer rechargeType;

    private Date createTime;

    private Date updateTime;


    public RechargeLog toRechargeLog(){
        RechargeLog log = new RechargeLog();
        log.setRelateId(id.longValue());
        log.setAccountId(accountId);
        log.setAmount(amount);
        log.setRebate(amount.multiply(benefits));
        log.setPayed(BigDecimal.ZERO);
        log.setRechargeStatus(0);
        log.setCreateTime(new Date());
        log.setUpdateTime(new Date());
        return log;
    }

    public RechargeRequestLog(AppRecharge appRecharge) {
        this.amount = appRecharge.getRechargeMoney();
        this.updateTime = appRecharge.getUpdateTime();
        this.benefits = appRecharge.getBenefitsMoney();
        this.createTime = appRecharge.getCreateTime();
        this.customerId = appRecharge.getAppUserId();
        this.status = appRecharge.getState();
        this.rechargeType = appRecharge.getRechargeType();
        this.transferFlow = appRecharge.getTransferFlow();
        this.remarks = appRecharge.getRemarks();
        this.customerMoney = appRecharge.getCustomerMoney();
        this.id = Long.parseLong(appRecharge.getRechargeId());
        if(null == appRecharge.getCustomerMoney()){
            this.customerMoney = appRecharge.getRechargeMoney();
        }
    }

    public RechargeRequestLog() {
    }
}