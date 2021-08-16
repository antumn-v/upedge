package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.UserApplicationListRequest;
/**
 * @author gx
 */
public class UserApplicationListResponse extends BaseResponse {
    public UserApplicationListResponse(int code, String msg, Object data,UserApplicationListRequest req) {
        super(code,msg,data,req);
    }
}
