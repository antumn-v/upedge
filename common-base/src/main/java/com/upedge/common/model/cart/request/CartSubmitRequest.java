package com.upedge.common.model.cart.request;

import lombok.Data;

import java.util.List;

@Data
public class CartSubmitRequest {

    List<Long> ids;
    
    Integer type;
}
