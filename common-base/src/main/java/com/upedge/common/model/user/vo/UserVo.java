package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by guoxing on 2020/11/9.
 */
@Data
public class UserVo extends UserInfoVo {
    private Long id;

    private Long customerId;

    private String loginName;

    private Integer userType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
