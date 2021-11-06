package com.upedge.pms.modules.product.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class GroupVariantVo {
    @Size(min = 2)
    List<GroupVo> list;
    @NotBlank
    String variantAttrEvalue;

}
