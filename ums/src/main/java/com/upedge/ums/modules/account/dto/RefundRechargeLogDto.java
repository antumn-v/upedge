package com.upedge.ums.modules.account.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RefundRechargeLogDto {

    private Long id;

    private Date updateTime;

    private BigDecimal upPayed;

    private BigDecimal upAmount;

    private BigDecimal upRebate;
}