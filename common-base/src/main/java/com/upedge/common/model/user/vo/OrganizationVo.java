package com.upedge.common.model.user.vo;

import lombok.Data;

@Data
public class OrganizationVo {

    private Long id;

    private Long customerId;

    private String orgPath;

    private String orgName;

    private Long orgParent;

    private String createTime;

    private String updateTime;

}
