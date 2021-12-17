package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerOrderCostVo {

    private String date;

    private BigDecimal productCost;

    private BigDecimal shipCost;
}
