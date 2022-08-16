package com.upedge.thirdparty.shipcompany.fpx.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetFpxWarehouseRequest {


    @JsonProperty("service_code")
    private String serviceCode;
    @JsonProperty("country")
    private String country;
}
