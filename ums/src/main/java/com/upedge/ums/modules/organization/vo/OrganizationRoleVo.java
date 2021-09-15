package com.upedge.ums.modules.organization.vo;

import com.upedge.common.model.user.vo.RoleVo;
import lombok.Data;

@Data
public class OrganizationRoleVo extends RoleVo {

    Long orgId;

    String orgName;

    Integer dataType;
}
