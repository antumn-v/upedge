package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.RoleMenu;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class RoleMenuUpdateRequest{

    Long roleId;

    /**
     * 
     */
    private Long menuId;

    public RoleMenu toRoleMenu(){
        RoleMenu roleMenu=new RoleMenu();
        roleMenu.setRoleId(roleId);
        roleMenu.setMenuId(menuId);
        return roleMenu;
    }

}
