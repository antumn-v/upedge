package com.upedge.oms.modules.order.vo;

import lombok.Data;

@Data
public class OrderInsightsVo {

    private Long storeId;
    private String storeName;
    private Integer orderCount;
    private Double profit;
    private Double income;
    private Double orderRefundAmount;
    private Double storeRefundAmount;
    private Double orderShipCost;
    private Double orderProductCost;
}
