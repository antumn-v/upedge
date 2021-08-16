package com.upedge.ums.modules.organization.request;

import com.upedge.ums.modules.organization.entity.OrganizationMenu;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OrganizationMenuUpdateRequest{

    Long orgId;

    /**
     * 
     */
    private Long menuId;

    public OrganizationMenu toOrganizationMenu(){
        OrganizationMenu organizationMenu=new OrganizationMenu();
        organizationMenu.setOrgId(orgId);
        organizationMenu.setMenuId(menuId);
        return organizationMenu;
    }

}
