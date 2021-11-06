package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;

public class MultiReleaseResponse extends BaseResponse {

    public MultiReleaseResponse(int code, String message, int res) {
        super(code,message,res);
    }

    public MultiReleaseResponse(int code, String message) {
        super(code,message);
    }
}
