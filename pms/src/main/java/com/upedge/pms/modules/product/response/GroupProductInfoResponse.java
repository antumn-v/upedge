package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;

public class GroupProductInfoResponse extends BaseResponse {
    public GroupProductInfoResponse(int code, String message, Object groupVariantInfoVoList){
        super(code,message,groupVariantInfoVoList);
    }

    public GroupProductInfoResponse(int code, String message) {
        super(code,message);
    }
}
