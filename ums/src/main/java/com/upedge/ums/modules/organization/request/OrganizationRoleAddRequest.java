package com.upedge.ums.modules.organization.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.organization.entity.OrganizationRole;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OrganizationRoleAddRequest{

    Long orgId;

    /**
    * 
    */
    private Long roleId;
    /**
    * 0:自己 1:本部门 2:本部门及子部门
    */
    private Integer dataType;

    public OrganizationRole toOrganizationRole(){
        OrganizationRole organizationRole=new OrganizationRole();
        organizationRole.setOrgId(orgId);
        organizationRole.setRoleId(roleId);
        organizationRole.setDataType(dataType);
        return organizationRole;
    }

}
