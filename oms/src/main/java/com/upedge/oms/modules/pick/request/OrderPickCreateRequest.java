package com.upedge.oms.modules.pick.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderPickCreateRequest {

    @NotNull
    private Integer pickType;

    @NotNull
    private List<Long> shipMethodIds;

    private List<Long> orderIds;

    private int singleProductQuantity;

    private int mixedProductQuantity;
}
