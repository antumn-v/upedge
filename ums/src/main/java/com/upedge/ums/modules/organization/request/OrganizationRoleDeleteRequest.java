package com.upedge.ums.modules.organization.request;

import lombok.Data;

@Data
public class OrganizationRoleDeleteRequest {

    Long orgId;

    Long roleId;
}
