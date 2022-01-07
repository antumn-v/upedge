package com.upedge.pms.modules.alibaba.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AlibabaVariantVo {

    String imageUrl;

    String attrName;

    String attrValue;

    List<ProductVariantVo> variantVoList=new ArrayList<>();

}
