package com.upedge.oms.modules.pack.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderPackRevokeRequest {

    @NotNull
    private List<Long> orderIds;

    private String reason;
}
