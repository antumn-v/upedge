package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.UserRoleListRequest;
/**
 * @author gx
 */
public class UserRoleListResponse extends BaseResponse {
    public UserRoleListResponse(int code, String msg, Object data,UserRoleListRequest req) {
        super(code,msg,data,req);
    }
}
