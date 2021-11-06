package com.upedge.oms.modules.pack.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PackageData {

    String dayDate;//日期
    Integer packageCount;
    //包裹支出金额
    BigDecimal packageAmount;
    //订单销售金额
    BigDecimal orderAmount;
}
