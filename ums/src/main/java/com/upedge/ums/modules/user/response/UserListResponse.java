package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.UserListRequest;
/**
 * @author gx
 */
public class UserListResponse extends BaseResponse {
    public UserListResponse(int code, String msg, Object data,UserListRequest req) {
        super(code,msg,data,req);
    }
}
