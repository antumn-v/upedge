package com.upedge.oms.modules.stock.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StockOrderUpdateTrackRequest {

    @NotNull
    private Long orderId;

    private String shipMethodName;

    @NotNull
    private Integer trackingType;

    @NotNull
    private String trackingCode;

}
