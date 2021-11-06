package com.upedge.oms.modules.statistics.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WaitTrackOrderDataDetailsRequest {

    @NotBlank
    private String adminUserId;

    @NotNull
    private Integer dayNum;

    /**
     * 1.普通订单
     * 2.批发订单
     */
    @NotNull
    private Integer type;

}
