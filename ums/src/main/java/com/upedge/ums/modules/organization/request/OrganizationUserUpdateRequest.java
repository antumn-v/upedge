package com.upedge.ums.modules.organization.request;

import com.upedge.ums.modules.organization.entity.OrganizationUser;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OrganizationUserUpdateRequest{


    Long orgId;
    /**
     * 
     */
    private Long userId;

    public OrganizationUser toOrganizationUser(){
        OrganizationUser organizationUser=new OrganizationUser();
        organizationUser.setOrgId(orgId);
        organizationUser.setUserId(userId);
        return organizationUser;
    }

}
