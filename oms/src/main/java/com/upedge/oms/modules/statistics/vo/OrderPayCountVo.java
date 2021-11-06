package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderPayCountVo {

    BigDecimal productAmount;

    BigDecimal shipPrice;

    BigDecimal productDischargeAmount;

    BigDecimal fixFee;

    BigDecimal vatAmount;

    BigDecimal payAmount;

    Long count;

    String date;
}
