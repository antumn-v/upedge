package com.upedge.common.model.pms.request;

import lombok.Data;

import java.util.List;

@Data
public class QuotedProductSelectBySkuRequest {

    private Long customerId;

    private List<String> skus;
}
