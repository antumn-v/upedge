package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProductVariantDisableRequest {
    /**
     * 变体ids
     */
    @Size(min = 1)
    private List<Long> ids;
}
