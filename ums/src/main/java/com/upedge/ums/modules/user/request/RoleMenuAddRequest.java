package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.RoleMenu;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class RoleMenuAddRequest{

    /**
    * 
    */
    private Long menuId;

    public RoleMenu toRoleMenu(){
        RoleMenu roleMenu=new RoleMenu();
        roleMenu.setMenuId(menuId);
        return roleMenu;
    }

}
