package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxMeasureUnit {


    @JSONField(name = "code")
    private String code;
    @JSONField(name = "name_cn")
    private String nameCn;
    @JSONField(name = "name_en")
    private String nameEn;
    @JSONField(name = "symbol")
    private String symbol;
}
