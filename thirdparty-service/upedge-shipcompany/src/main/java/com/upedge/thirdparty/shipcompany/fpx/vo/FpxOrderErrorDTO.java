package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxOrderErrorDTO {


    @JSONField(name = "error_code")
    private String errorCode;
    @JSONField(name = "error_msg")
    private String errorMsg;
    @JSONField(name = "reference_code")
    private String referenceCode;
}
