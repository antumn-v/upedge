package com.upedge.common.model.product.request;

import lombok.Data;

import java.util.List;

@Data
public class RelateDetailSearchRequest {

    List<Long> storeVariantIds;
}
