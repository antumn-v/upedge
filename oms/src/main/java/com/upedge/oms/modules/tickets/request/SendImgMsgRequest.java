package com.upedge.oms.modules.tickets.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SendImgMsgRequest {

    @NotBlank
    private String base64String;

    @NotNull
    private Long ticketId;

}
