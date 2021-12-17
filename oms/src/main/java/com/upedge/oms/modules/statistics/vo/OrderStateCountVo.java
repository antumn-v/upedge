package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

@Data
public class OrderStateCountVo {

    private long paidOrderCount;

    private long refundOrderCount;

    private long undeliverableOrderCount;
}
