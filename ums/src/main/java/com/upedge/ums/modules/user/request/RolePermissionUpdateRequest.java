package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.RolePermission;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class RolePermissionUpdateRequest{

    Long roleId;
    /**
     * 
     */
    private String permission;

    public RolePermission toRolePermission(){
        RolePermission rolePermission=new RolePermission();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermission(permission);
        return rolePermission;
    }

}
