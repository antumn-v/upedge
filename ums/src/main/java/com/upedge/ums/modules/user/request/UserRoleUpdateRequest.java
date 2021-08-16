package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.UserRole;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class UserRoleUpdateRequest{

    Long userId;
    /**
     * 
     */
    private Long roleId;

    public UserRole toUserRole(){
        UserRole userRole=new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }

}
