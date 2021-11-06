package com.upedge.oms.modules.common.vo;

import lombok.Data;

@Data
public class OrderCommonReshipInfoVo {
    /**
     *
     */
    private Long orderId;
    /**
     *
     */
    private Long originalOrderId;
    /**
     *
     */
    private String reason;
    /**
     * 补发次数
     */
    private Integer reshipTimes;
}
