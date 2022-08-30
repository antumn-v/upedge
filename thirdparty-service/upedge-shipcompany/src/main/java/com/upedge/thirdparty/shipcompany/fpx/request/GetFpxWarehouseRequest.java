package com.upedge.thirdparty.shipcompany.fpx.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GetFpxWarehouseRequest {


    @JSONField(name = "service_code")
    private String serviceCode;
    @JSONField(name = "country")
    private String country;
}
