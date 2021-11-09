package com.upedge.pms.modules.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ImportProductPublishRequest {

    @Size(min = 1)
    List<Long> productIds;

    @NotNull
    Long storeId;
}
