package com.upedge.ums.modules.account.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 海桐
 */
@Data
public class RechargeLog {
    private Long id;

    private Long accountId;

    private Long relateId;

    private BigDecimal amount;

    private BigDecimal rebate;

    private BigDecimal payed;

    private Integer rechargeStatus;

    private Integer rechargeType = 0;

    private Date createTime;

    private Date updateTime;


}