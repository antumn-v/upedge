package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OrderRefundUpdateRemarkRequest {
    @NotNull
    private Long id;
    @NotBlank
    private String remark;

}
