package com.upedge.ums.modules.account.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RechargeLogVo {

    private Long accountId;

    private Integer relateId;

    private BigDecimal amount;

    private BigDecimal rebate;

    private BigDecimal payed;

    private Integer rechargeStatus;

    private Integer rechargeType = 0;
}
