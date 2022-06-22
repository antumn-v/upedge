package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ProductVariantAttr;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author gx
 */
@Data
public class ProductVariantUpdateRequest{

    Long id;

    @Size(min = 1,message = "变体属性不能为空")
    List<ProductVariantAttr> variantAttrs;

}
