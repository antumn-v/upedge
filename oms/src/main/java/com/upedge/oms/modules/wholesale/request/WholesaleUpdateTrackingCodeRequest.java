package com.upedge.oms.modules.wholesale.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WholesaleUpdateTrackingCodeRequest {

    @NotNull
    Long id;

    @NotBlank
    String trackingCode;
}
