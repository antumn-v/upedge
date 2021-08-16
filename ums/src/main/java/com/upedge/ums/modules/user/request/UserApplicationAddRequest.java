package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.UserApplication;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class UserApplicationAddRequest{

    /**
    * 
    */
    private Long applicationId;

    public UserApplication toUserApplication(){
        UserApplication userApplication=new UserApplication();
        userApplication.setApplicationId(applicationId);
        return userApplication;
    }

}
