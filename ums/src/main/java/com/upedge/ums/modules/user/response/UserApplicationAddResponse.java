package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.UserApplicationAddRequest;
/**
 * @author gx
 */
public class UserApplicationAddResponse extends BaseResponse {
    public UserApplicationAddResponse(int code, String msg, Object data, UserApplicationAddRequest req) {
        super(code,msg,data,req);
    }
}
