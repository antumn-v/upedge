package com.upedge.thirdparty.shipcompany.cne.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CneShipMethodDto {


    @JsonProperty("oName")
    private String oName;
    @JsonProperty("cName")
    private String cName;
}
