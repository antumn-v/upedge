package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;

public class CustomerSignUpResponse extends BaseResponse {

    private static final long serialVersionUID = -2415703966202380742L;

    public CustomerSignUpResponse(int code, String message) {
        super(code, message);
    }


    public CustomerSignUpResponse(int code, String message, Object data) {
        super(code, message,data);
    }


}