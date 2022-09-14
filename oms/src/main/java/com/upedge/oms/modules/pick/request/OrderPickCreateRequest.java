package com.upedge.oms.modules.pick.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderPickCreateRequest {

    private Integer pickType;

    private List<Long> shipMethodIds;

    private List<Long> orderIds;

    private int singleProductQuantity;

    private int mixedProductQuantity;
}
