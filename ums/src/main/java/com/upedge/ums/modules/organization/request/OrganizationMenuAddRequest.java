package com.upedge.ums.modules.organization.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.organization.entity.OrganizationMenu;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OrganizationMenuAddRequest{

    /**
    * 
    */
    private Long menuId;

    public OrganizationMenu toOrganizationMenu(){
        OrganizationMenu organizationMenu=new OrganizationMenu();
        organizationMenu.setMenuId(menuId);
        return organizationMenu;
    }

}
