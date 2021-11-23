package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.Set;

@Data
public class VariantAttributeVo {

    private Long productId;

    private String attributeCName;

    private String attributeEName;

    private Set<String> attributeEValues;

    private Set<String> attributeCValues;
}
