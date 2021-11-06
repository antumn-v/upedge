package com.upedge.common.model.user.request;

import lombok.Data;


@Data
public class OrderBenefitsRequest {

    private String startDay;
    private String endDay;
    private Long orderSourceId;
    private Double usdRate;

    public OrderBenefitsRequest(String startDay, String endDay, Long orderSourceId, Double usdRate) {
        this.startDay = startDay;
        this.endDay = endDay;
        this.orderSourceId = orderSourceId;
        this.usdRate = usdRate;
    }

    public OrderBenefitsRequest() {
    }
}
