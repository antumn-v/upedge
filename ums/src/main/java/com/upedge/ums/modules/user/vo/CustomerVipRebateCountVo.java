package com.upedge.ums.modules.user.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerVipRebateCountVo {

    Long customerId;

    String date;

    Long orderCount;

    BigDecimal totalRebate;
}
