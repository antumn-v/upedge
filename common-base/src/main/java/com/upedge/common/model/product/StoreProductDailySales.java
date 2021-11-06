package com.upedge.common.model.product;

import lombok.Data;

import java.util.List;

@Data
public class StoreProductDailySales {

    Long storeProductId;

    private Long storeId;

    private Long orgId;
    /**
     *
     */
    private Long customerId;

    List<DailySales> dailySales;
}
