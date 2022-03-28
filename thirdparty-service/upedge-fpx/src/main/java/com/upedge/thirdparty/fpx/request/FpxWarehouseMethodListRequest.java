package com.upedge.thirdparty.fpx.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxWarehouseMethodListRequest {


    @JsonProperty("service_code")
    private String serviceCode;
    @JsonProperty("category_code")
    private String categoryCode;
    @JsonProperty("source_position_code")
    private String sourcePositionCode;
    @JsonProperty("dest_position_code")
    private String destPositionCode;
    @JsonProperty("dest_country_code")
    private String destCountryCode;
    @JsonProperty("dest_post_code")
    private String destPostCode;
    @JsonProperty("dest_state_name")
    private String destStateName;
    @JsonProperty("dest_city_name")
    private String destCityName;
}
