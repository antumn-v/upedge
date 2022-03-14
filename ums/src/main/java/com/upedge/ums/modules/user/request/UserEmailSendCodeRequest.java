package com.upedge.ums.modules.user.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserEmailSendCodeRequest {

    @NotBlank
    String email;
}
