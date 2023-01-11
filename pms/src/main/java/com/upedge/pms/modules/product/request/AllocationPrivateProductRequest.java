package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class AllocationPrivateProductRequest {

    @Size(min = 1)
    List<Long> productIds;

    List<Long> customerIds;

    List<Long> storeIds;
}
