package com.upedge.tms.modules.ship.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ShipUnitFreightCalculationRequest {

    @NotNull
    private Long methodId;

    @NotNull
    private Long areaId;

    @NotNull
    private BigDecimal weight;
}
