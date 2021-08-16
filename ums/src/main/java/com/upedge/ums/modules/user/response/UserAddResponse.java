package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.UserAddRequest;
/**
 * @author gx
 */
public class UserAddResponse extends BaseResponse {
    public UserAddResponse(int code, String msg, Object data, UserAddRequest req) {
        super(code,msg,data,req);
    }
}
