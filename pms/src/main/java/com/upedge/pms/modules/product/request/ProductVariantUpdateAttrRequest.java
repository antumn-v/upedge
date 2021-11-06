package com.upedge.pms.modules.product.request;


import com.upedge.pms.modules.product.vo.VariantAttrVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProductVariantUpdateAttrRequest {

    @NotNull
    private Long productId;

    @Size(min = 1)
    private List<VariantAttrVo> variantAttrVoList;

}
