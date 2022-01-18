package com.upedge.oms.modules.cart.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CartAddStockRequest {

    @NotNull
    private Long variantId;

    private Integer quantity;


}
