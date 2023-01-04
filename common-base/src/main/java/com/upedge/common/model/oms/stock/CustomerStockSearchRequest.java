package com.upedge.common.model.oms.stock;

import lombok.Data;

import java.util.List;

@Data
public class CustomerStockSearchRequest {

    private Long customerId;

    private List<Long> variantIds;
}
