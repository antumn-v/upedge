package com.upedge.ums.modules.account.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 海桐
 */
@Data
public class Account {
    private Long id;

    private Long customerId;

    private String name;

    private BigDecimal balance;

    private BigDecimal rebate;

    private BigDecimal credit;

    private BigDecimal creditLimit;

    private BigDecimal commission;

    private Integer status;

    private Boolean isDefault;

}