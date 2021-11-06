package com.upedge.oms.modules.tickets.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SendTextMsgRequest {

    @NotNull
    private Long ticketId;
    @NotBlank
    private String message;

}
