package com.upedge.oms.modules.statistics.request;

import lombok.Data;

import java.util.Date;


/**
 * 按天统计退款   根据用户分组
 */
@Data
public class OrderRefundDailyCountRequest {
    Long refundId;
    Integer orderType;

    Date refundTime;

}
