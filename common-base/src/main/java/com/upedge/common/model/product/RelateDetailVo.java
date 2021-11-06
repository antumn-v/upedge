package com.upedge.common.model.product;

import lombok.Data;

import java.util.List;

@Data
public class RelateDetailVo {

    Long storeVariantId;

    Long storeProductId;

    Long adminProductId;

    Long adminVariantId;

    Long shippingId;

    Integer variantType;

    Integer scale;

    List<RelateVariantVo> relateVariantVos;
}
