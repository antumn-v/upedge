package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CustomerVo extends UserInfoVo {

    private String loginName;

    private String customerManager;

    private Date createTime;

}
