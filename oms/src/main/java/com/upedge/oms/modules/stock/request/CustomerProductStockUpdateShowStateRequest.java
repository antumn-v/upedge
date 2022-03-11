package com.upedge.oms.modules.stock.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerProductStockUpdateShowStateRequest {

    @NotNull
    Long id;

    @NotNull
    Integer state;
}
