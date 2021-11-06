package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CustomerPrivateProductCreatRequest {

    private List<Long> customerIds;

    @NotNull(message = "产品id不能为null")
    private Long productId;


}
