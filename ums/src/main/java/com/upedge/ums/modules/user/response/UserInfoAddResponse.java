package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.UserInfoAddRequest;
/**
 * @author gx
 */
public class UserInfoAddResponse extends BaseResponse {
    public UserInfoAddResponse(int code, String msg, Object data, UserInfoAddRequest req) {
        super(code,msg,data,req);
    }
}
