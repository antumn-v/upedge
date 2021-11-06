package com.upedge.oms.modules.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductAmountVo {

    Long id;

    BigDecimal productAmount;

    BigDecimal cnyProductAmount;
}
