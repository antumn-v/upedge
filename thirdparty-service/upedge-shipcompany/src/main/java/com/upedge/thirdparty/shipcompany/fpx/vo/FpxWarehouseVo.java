package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxWarehouseVo {


    @JsonProperty("warehouse_code")
    private String warehouseCode;
    @JsonProperty("warehouse_name_cn")
    private String warehouseNameCn;
    @JsonProperty("warehouse_name_en")
    private String warehouseNameEn;
    @JsonProperty("country")
    private String country;
    @JsonProperty("service_code")
    private String serviceCode;
}
