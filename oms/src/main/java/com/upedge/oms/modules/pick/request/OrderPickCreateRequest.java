package com.upedge.oms.modules.pick.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderPickCreateRequest {

    @NotNull
    private Integer pickType;

    @NotNull
    private Long shipMethodId;

    private int singleProductQuantity;

    private int mixedProductQuantity;
}
