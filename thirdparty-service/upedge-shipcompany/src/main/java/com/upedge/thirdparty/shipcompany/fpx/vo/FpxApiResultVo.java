package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.upedge.thirdparty.shipcompany.fpx.model.ResponseError;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxApiResultVo {

    @JSONField(name = "result")
    private String result;
    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "errors")
    private ResponseError errors;
    @JSONField(name = "data")
    private Object data;


}
