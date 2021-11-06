package com.upedge.pms.modules.product.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ProductGroupVo {
    @NotBlank
    String productTitle;

    List<GroupVariantVo> groupVariantVoList;

}
