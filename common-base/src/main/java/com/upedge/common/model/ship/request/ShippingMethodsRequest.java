package com.upedge.common.model.ship.request;

import lombok.Data;

import java.util.List;

@Data
public class ShippingMethodsRequest {

    private List<Long> ids;

}
