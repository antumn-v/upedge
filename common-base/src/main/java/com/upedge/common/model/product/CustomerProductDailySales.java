package com.upedge.common.model.product;

import lombok.Data;

import java.util.List;

@Data
public class CustomerProductDailySales {

    Long customerId;

    Long productId;

    Long variantId;

    List<DailySales> dailySalesList;
}
