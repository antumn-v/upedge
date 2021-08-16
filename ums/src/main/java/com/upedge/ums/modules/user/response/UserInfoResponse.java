package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author gx
 */
public class UserInfoResponse extends BaseResponse {
    public UserInfoResponse(int code, String msg, Object data, Object req) {
        super(code,msg,data,req);
    }
}
