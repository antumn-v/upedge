package com.upedge.pms.modules.product.vo;

import lombok.Data;

import java.util.List;

@Data
public class SplitVariantIdVo {

    String parentVariantId;

    List<Long> splitVariantIds;
}
