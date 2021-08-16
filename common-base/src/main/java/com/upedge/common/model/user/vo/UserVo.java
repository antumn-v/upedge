package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by jiaqi on 2020/11/9.
 */
@Data
public class UserVo {
    private Long id;

    private Long customerId;

    private String loginName;

    private String loginPass;

    private Integer userType;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}
