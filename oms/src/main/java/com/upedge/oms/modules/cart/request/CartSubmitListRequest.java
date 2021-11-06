package com.upedge.oms.modules.cart.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartSubmitListRequest {

    @NotNull
    private Integer cartType;

    @NotNull
    private List<Long> ids;
}
