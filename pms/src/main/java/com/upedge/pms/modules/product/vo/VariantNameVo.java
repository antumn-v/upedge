package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class VariantNameVo {

    private String cName;
    private String eName;

    private List<VariantValueVo> values;
}
