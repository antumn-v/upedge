package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.StoreProductImageListRequest;

/**
 * @author author
 */
public class StoreProductImageListResponse extends BaseResponse {
    public StoreProductImageListResponse(int code, String msg, Object data, StoreProductImageListRequest req) {
        super(code,msg,data,req);
    }
}
