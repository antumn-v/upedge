package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.UserRoleAddRequest;
/**
 * @author gx
 */
public class UserRoleAddResponse extends BaseResponse {
    public UserRoleAddResponse(int code, String msg, Object data, UserRoleAddRequest req) {
        super(code,msg,data,req);
    }
}
