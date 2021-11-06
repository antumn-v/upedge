package com.upedge.oms.modules.tickets.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class OpenTicketRequest {

    @NotNull
    private Long orderId;
    @NotBlank
    private String msg;

}
