package com.upedge.ums.modules.user.response;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.UserProfileVo;

/**
 * Created by guoxing on 2020/10/26.
 */
public class UserProfileResponse extends BaseResponse {
    public UserProfileResponse(int code, String message, UserProfileVo data) {
        super(code,message,data);
    }

    public UserProfileResponse(int code, String message) {
        super(code,message);
    }
}
