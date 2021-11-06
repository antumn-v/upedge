package com.upedge.ums.modules.store.response;


import com.upedge.common.base.BaseResponse;

public class WoocommerceAuthResponse extends BaseResponse {

    public WoocommerceAuthResponse(int code, String msg) {
        super(code, msg);
    }

    public WoocommerceAuthResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
