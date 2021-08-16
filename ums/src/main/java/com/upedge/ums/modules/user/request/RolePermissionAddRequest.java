package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.RolePermission;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class RolePermissionAddRequest{

    /**
    * 
    */
    private String permission;

    public RolePermission toRolePermission(){
        RolePermission rolePermission=new RolePermission();
        rolePermission.setPermission(permission);
        return rolePermission;
    }

}
