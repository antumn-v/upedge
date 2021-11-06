package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProductAllocateRequest {

    @NotNull
    private Long userId;

    @Size(min = 1)
    private List<Long> productIds;
}
