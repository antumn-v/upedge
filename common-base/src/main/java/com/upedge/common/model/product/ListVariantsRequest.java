package com.upedge.common.model.product;

import lombok.Data;

import java.util.List;

@Data
public class ListVariantsRequest {

    private List<Long> variantIds;

    private List<String> variantSkus;
}
