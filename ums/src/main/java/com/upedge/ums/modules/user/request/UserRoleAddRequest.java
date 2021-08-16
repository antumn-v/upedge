package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.UserRole;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class UserRoleAddRequest{

    /**
    * 
    */
    private Long roleId;

    public UserRole toUserRole(){
        UserRole userRole=new UserRole();
        userRole.setRoleId(roleId);
        return userRole;
    }

}
