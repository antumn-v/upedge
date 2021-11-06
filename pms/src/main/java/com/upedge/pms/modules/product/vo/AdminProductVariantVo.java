package com.upedge.pms.modules.product.vo;

import com.upedge.pms.modules.product.entity.ProductVariant;
import lombok.Data;

import java.util.List;

@Data
public class AdminProductVariantVo {

    private List<ProductVariant> productVariantList;

    private List<VariantAttrVo> variantAttrVoList;
}
