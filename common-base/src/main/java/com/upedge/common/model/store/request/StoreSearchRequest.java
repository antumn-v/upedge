package com.upedge.common.model.store.request;

import lombok.Data;

@Data
public class StoreSearchRequest {
    private Long id;

    private String storeUrl;

    private Long orgId;

}
