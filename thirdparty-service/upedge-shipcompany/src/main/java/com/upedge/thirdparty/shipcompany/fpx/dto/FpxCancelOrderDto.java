package com.upedge.thirdparty.shipcompany.fpx.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxOrderErrorDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FpxCancelOrderDto {


    @JsonProperty("result")
    private String result;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("errors")
    private List<FpxOrderErrorDTO> errors;
    @JsonProperty("data")
    private Object data;

}
