package com.upedge.ums.modules.store.response;

import com.upedge.common.base.BaseResponse;

public class ShopifyAuthResponse extends BaseResponse {
    private static final long serialVersionUID = 6231818188463534296L;

    public ShopifyAuthResponse(int code, String msg) {
        super(code, msg);
    }

    public ShopifyAuthResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
