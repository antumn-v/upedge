package com.upedge.common.model.user.request;

import lombok.Data;

@Data
public class UserInfoSelectRequest {

    Long userId;

    String email;

    String loginName;

}
