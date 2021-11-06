package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.ProductAttr;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateProductAttrRequest {

    @Size(min = 1)
    List<ProductAttr> attrList;

}
