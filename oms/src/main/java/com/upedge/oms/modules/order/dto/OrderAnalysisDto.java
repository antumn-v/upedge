package com.upedge.oms.modules.order.dto;

import lombok.Data;

/**
 * 客户管理 -- 个人客户  -- 用户信息 -- 订单分析  查询参数
 */
@Data
public class OrderAnalysisDto {

    /**
     * 客户的id
     */
    private String customerId;

    /**
     * 查询开始时间
     */
    private String startTime;

    /**
     *  查询结束时间
     */
    private String endTime;
}
