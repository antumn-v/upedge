package com.upedge.thirdparty.fpx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upedge.thirdparty.fpx.dto.PriceCalculatorDTO;
import com.upedge.thirdparty.fpx.model.ResponseError;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PriceCalculatorResultVo {

    @JsonProperty("result")
    private String result;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("errors")
    private ResponseError errors;
    @JsonProperty("data")
    private List<PriceCalculatorDTO> data;


}
