package com.upedge.oms.modules.statistics.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WaitTrackOrderDataRequest {

    @NotNull
    private Integer dayNum;

}
