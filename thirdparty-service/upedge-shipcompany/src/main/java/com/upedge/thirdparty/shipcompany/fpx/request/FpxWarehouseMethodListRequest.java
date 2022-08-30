package com.upedge.thirdparty.shipcompany.fpx.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxWarehouseMethodListRequest {


    @JSONField(name = "service_code")
    private String serviceCode;
    @JSONField(name = "category_code")
    private String categoryCode;
    @JSONField(name = "source_position_code")
    private String sourcePositionCode;
    @JSONField(name = "dest_position_code")
    private String destPositionCode;
    @JSONField(name = "dest_country_code")
    private String destCountryCode;
    @JSONField(name = "dest_post_code")
    private String destPostCode;
    @JSONField(name = "dest_state_name")
    private String destStateName;
    @JSONField(name = "dest_city_name")
    private String destCityName;
}
