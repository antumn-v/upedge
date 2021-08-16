package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.UserApplication;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class UserApplicationUpdateRequest{

    Long userId;
    /**
     * 
     */
    private Long applicationId;

    public UserApplication toUserApplication(){
        UserApplication userApplication=new UserApplication();
        userApplication.setUserId(userId);
        userApplication.setApplicationId(applicationId);
        return userApplication;
    }

}
