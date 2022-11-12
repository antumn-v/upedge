package com.upedge.oms.modules.pick.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OrderPickWaveReleaseRequest {

    @NotNull
    private Integer waveNo;
}
