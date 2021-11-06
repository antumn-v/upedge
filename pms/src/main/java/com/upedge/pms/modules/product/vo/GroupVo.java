package com.upedge.pms.modules.product.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class GroupVo {
    @NotNull
    private Long variantId;
    @NotNull
    private Long productId;
    @NotNull
    @Min(value = 1)
    private Integer quantity;
}
