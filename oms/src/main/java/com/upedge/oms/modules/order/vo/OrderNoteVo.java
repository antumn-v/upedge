package com.upedge.oms.modules.order.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderNoteVo {

    @NotNull
    Long orderId;

    String note;
}
