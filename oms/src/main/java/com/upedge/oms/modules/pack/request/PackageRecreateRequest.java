package com.upedge.oms.modules.pack.request;

import lombok.Data;

import java.util.List;

@Data
public class PackageRecreateRequest {

    private List<Long> orderIds;

    private Long shipMethodId;
}
