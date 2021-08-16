package com.upedge.ums.modules.application.request;

import com.upedge.ums.modules.application.entity.MenuPermission;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class MenuPermissionUpdateRequest{

    /**
     * 
     */
    private Long menuId;
    /**
     * 
     */
    private String permission;

    public MenuPermission toMenuPermission(Long id){
        MenuPermission menuPermission=new MenuPermission();
        menuPermission.setId(id);
        menuPermission.setMenuId(menuId);
        menuPermission.setPermission(permission);
        return menuPermission;
    }

}
