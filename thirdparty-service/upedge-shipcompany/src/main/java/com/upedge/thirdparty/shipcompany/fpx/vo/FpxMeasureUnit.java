package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxMeasureUnit {


    @JsonProperty("code")
    private String code;
    @JsonProperty("name_cn")
    private String nameCn;
    @JsonProperty("name_en")
    private String nameEn;
    @JsonProperty("symbol")
    private String symbol;
}
