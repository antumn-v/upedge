package com.upedge.oms.modules.pack.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DailyOrderHandleVo {

    private BigDecimal orderHandlePercentage;

    private Integer day;

    private long orderCount;

    private long totalHandleTime;
}
