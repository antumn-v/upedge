package com.upedge.thirdparty.shipcompany.fpx.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ShipPriceCalculator {


    @JSONField(name = "service_code")
    private String service_code;
    @JSONField(name = "billing_time")
    private Long billing_time;
    @JSONField(name = "product_codes")
    private List<String> product_codes;
    @JSONField(name = "warehouse_code")
    private String warehouse_code;
    @JSONField(name = "length")
    private String length;
    @JSONField(name = "width")
    private String width;
    @JSONField(name = "height")
    private String height;
    @JSONField(name = "weight")
    private String weight;
    @JSONField(name = "cargo_units")
    private List<CargoUnitsDTO> cargo_units;
    @JSONField(name = "destination")
    private DestinationDTO destination;

    @NoArgsConstructor
    @Data
    public static class DestinationDTO {
        @JSONField(name = "post_code")
        private String post_code;
        @JSONField(name = "country")
        private String country;
    }

    @NoArgsConstructor
    @Data
    public static class CargoUnitsDTO {
        @JSONField(name = "value")
        private Integer value;
        @JSONField(name = "unit")
        private String unit;
    }
}
