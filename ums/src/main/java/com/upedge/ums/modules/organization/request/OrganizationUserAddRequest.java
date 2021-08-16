package com.upedge.ums.modules.organization.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.organization.entity.OrganizationUser;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OrganizationUserAddRequest{

    /**
    * 
    */
    private Long userId;

    public OrganizationUser toOrganizationUser(){
        OrganizationUser organizationUser=new OrganizationUser();
        organizationUser.setUserId(userId);
        return organizationUser;
    }

}
