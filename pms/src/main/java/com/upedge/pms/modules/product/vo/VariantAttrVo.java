package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VariantAttrVo {

    private String cname;

    private String ename;

    private List<VariantValVo> variantValVoList=new ArrayList<>();
}
