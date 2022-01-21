package com.upedge.ums.modules.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserSignInRequest {

    @NotNull
    private String loginName;

    @NotNull
    private String password;

    @NotNull
    private Long applicationId;

    private String state;


}
