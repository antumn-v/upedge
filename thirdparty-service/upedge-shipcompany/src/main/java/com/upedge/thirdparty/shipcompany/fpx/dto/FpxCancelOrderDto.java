package com.upedge.thirdparty.shipcompany.fpx.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.upedge.thirdparty.shipcompany.fpx.vo.FpxOrderErrorDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FpxCancelOrderDto {


    @JSONField(name = "result")
    private String result;
    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "errors")
    private List<FpxOrderErrorDTO> errors;
    @JSONField(name = "data")
    private Object data;

}
