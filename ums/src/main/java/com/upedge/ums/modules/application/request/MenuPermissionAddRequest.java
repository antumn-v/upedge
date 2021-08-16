package com.upedge.ums.modules.application.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.application.entity.MenuPermission;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class MenuPermissionAddRequest{

    /**
    * 
    */
    private Long menuId;
    /**
    * 
    */
    private String permission;

    public MenuPermission toMenuPermission(){
        MenuPermission menuPermission=new MenuPermission();
        menuPermission.setMenuId(menuId);
        menuPermission.setPermission(permission);
        return menuPermission;
    }

}
