package com.upedge.thirdparty.fpx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxMethodVo {

    //运输方式代码
    @JsonProperty("logistics_product_code")
    private String logisticsProductCode;
    //运输方式中文名
    @JsonProperty("logistics_product_name_cn")
    private String logisticsProductNameCn;
    //运输方式英文名
    @JsonProperty("logistics_product_name_en")
    private String logisticsProductNameEn;
}
