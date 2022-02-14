package com.upedge.thirdparty.fpx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ShipPriceCalculator {


    @JsonProperty("service_code")
    private String service_code;
    @JsonProperty("billing_time")
    private Long billing_time;
    @JsonProperty("product_codes")
    private List<String> product_codes;
    @JsonProperty("warehouse_code")
    private String warehouse_code;
    @JsonProperty("length")
    private String length;
    @JsonProperty("width")
    private String width;
    @JsonProperty("height")
    private String height;
    @JsonProperty("weight")
    private String weight;
    @JsonProperty("cargo_units")
    private List<CargoUnitsDTO> cargo_units;
    @JsonProperty("destination")
    private DestinationDTO destination;

    @NoArgsConstructor
    @Data
    public static class DestinationDTO {
        @JsonProperty("post_code")
        private String post_code;
        @JsonProperty("country")
        private String country;
    }

    @NoArgsConstructor
    @Data
    public static class CargoUnitsDTO {
        @JsonProperty("value")
        private Integer value;
        @JsonProperty("unit")
        private String unit;
    }
}
