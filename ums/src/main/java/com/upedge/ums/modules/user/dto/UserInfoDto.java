package com.upedge.ums.modules.user.dto;

import lombok.Data;

@Data
public class UserInfoDto {
    private Long id;

    private String username;

    private String email;

    private String loginName;
}
