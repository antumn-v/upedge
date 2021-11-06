package com.upedge.oms.modules.statistics.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardOrderDto {

    String dayDate;//日期

    Long orderPaidNum;//付款订单数

    BigDecimal orderAmount;//付款订单金额

    Long orderNum;//客户订单数

}
