package com.upedge.oms.modules.pack.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderHandleTimeRequest extends PackageDailyCountRequest {

    private Integer maxHandleDay;

    private Integer orderSourceId;

    @NotNull(message = "時效为null！")
    private Integer handleType = 0;

    String typeField;
}
