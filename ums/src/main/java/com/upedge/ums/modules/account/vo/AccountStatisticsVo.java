package com.upedge.ums.modules.account.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountStatisticsVo {
    //客户剩余余额
    private BigDecimal userRestBalanceAmount;
    //已开房信用额度
    private BigDecimal openCredit;
    //已使用信用额度
    private BigDecimal usedCredit;
}
