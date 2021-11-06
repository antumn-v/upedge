package com.upedge.oms.modules.stock.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateProcurementRequest {

    @NotNull
    private Long id;

}
