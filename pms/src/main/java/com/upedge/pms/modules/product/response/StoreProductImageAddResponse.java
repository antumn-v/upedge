package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.StoreProductImageAddRequest;

/**
 * @author author
 */
public class StoreProductImageAddResponse extends BaseResponse {
    public StoreProductImageAddResponse(int code, String msg, Object data, StoreProductImageAddRequest req) {
        super(code,msg,data,req);
    }
}
