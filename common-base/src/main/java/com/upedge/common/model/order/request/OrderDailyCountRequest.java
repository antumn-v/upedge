package com.upedge.common.model.order.request;

import lombok.Data;

/**
 * 统计查询使用
 */
@Data
public class OrderDailyCountRequest {

    private String managerCode;

    private String thisDay;

    private String startDay;

    private String endDay;

    private Integer orderType;

    private Integer noOrderType;
}
