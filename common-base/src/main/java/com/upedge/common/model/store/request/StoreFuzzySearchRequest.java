package com.upedge.common.model.store.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StoreFuzzySearchRequest {

    @NotBlank
    private String storeName;
}
