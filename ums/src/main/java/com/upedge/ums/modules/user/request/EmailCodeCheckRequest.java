package com.upedge.ums.modules.user.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmailCodeCheckRequest {

    @NotBlank
    String email;

    @NotBlank
    String code;
}
