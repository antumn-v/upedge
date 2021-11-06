package com.upedge.ums.modules.user.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountVo {

    private Long id;

    private Long customerId;

    private BigDecimal balance;

    private BigDecimal rebate;

    private BigDecimal credit;

    private BigDecimal creditLimit;

    private BigDecimal commission;

    private Integer status;

    private String username;

    private String email;

    private String loginName;
}
