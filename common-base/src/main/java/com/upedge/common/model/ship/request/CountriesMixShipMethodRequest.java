package com.upedge.common.model.ship.request;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class CountriesMixShipMethodRequest {

    @NotNull
    List<String> countries;

    public CountriesMixShipMethodRequest() {
    }
}
