package com.upedge.thirdparty.shipcompany.fpx.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderPackageGetLabelRequest {

    @NotNull
    private Long packNo;

}
