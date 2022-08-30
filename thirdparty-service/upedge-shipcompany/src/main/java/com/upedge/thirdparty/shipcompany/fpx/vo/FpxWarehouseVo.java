package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxWarehouseVo {


    @JSONField(name = "warehouse_code")
    private String warehouseCode;
    @JSONField(name = "warehouse_name_cn")
    private String warehouseNameCn;
    @JSONField(name = "warehouse_name_en")
    private String warehouseNameEn;
    @JSONField(name = "country")
    private String country;
    @JSONField(name = "service_code")
    private String serviceCode;
}
