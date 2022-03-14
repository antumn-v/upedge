package com.upedge.ums.modules.user.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserRecoverPasswordRequest {

    @NotBlank
    String email;

    @NotBlank
    String code;

    @NotBlank
    String newPass;

    @NotNull
    Long applicationId;
}
