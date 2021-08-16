package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.Date;

@Data
public class OrganizationVo {

    private Long id;

    private Long customerId;

    private String orgPath;

    private String orgName;

    private Long orgParent;

    private Date createTime;

    private Date updateTime;

}
