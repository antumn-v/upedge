package com.upedge.thirdparty.fpx.request;

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
