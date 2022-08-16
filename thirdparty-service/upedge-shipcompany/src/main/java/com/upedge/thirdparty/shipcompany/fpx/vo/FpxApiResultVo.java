package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upedge.thirdparty.shipcompany.fpx.model.ResponseError;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxApiResultVo {

    @JsonProperty("result")
    private String result;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("errors")
    private ResponseError errors;
    @JsonProperty("data")
    private Object data;


}
