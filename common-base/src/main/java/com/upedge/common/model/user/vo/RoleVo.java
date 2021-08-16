package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by jiaqi on 2020/11/9.
 */
@Data
public class RoleVo {
    private Long id;

    private Long customerId;

    private String roleCode;

    private String roleName;

    private Date createTime;

    private Date updateTime;

    private Long applicationId;
}
