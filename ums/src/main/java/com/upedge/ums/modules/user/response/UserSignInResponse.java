package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseException;
import com.upedge.common.base.BaseResponse;

public class UserSignInResponse extends BaseResponse {

    public UserSignInResponse(int code, String msg) {
        super(code, msg);
    }

    public UserSignInResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public UserSignInResponse(BaseException e) {
        super(e);
    }
}
