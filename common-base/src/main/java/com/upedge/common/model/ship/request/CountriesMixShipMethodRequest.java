package com.upedge.common.model.ship.request;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CountriesMixShipMethodRequest {

    @NotNull
    String countries;

    public CountriesMixShipMethodRequest() {
    }
}
