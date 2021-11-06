package com.upedge.common.model.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ManagerOrderStatisticsVo extends OrderStatisticsVo  {

    String managerCode;

    Integer orderType;

    Integer customerPlaceOrderCount;

    BigDecimal usdRate = new BigDecimal("6.3");
}
