package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxMethodVo {

    //运输方式代码
    @JSONField(name = "logistics_product_code")
    private String logisticsProductCode;
    //运输方式中文名
    @JSONField(name = "logistics_product_name_cn")
    private String logisticsProductNameCn;
    //运输方式英文名
    @JSONField(name = "logistics_product_name_en")
    private String logisticsProductNameEn;
}
