package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.StoreCustomProductListRequest;

/**
 * @author xwCui
 */
public class StoreCustomProductListResponse extends BaseResponse {
    public StoreCustomProductListResponse(int code, String msg, Object data, StoreCustomProductListRequest req) {
        super(code,msg,data,req);
    }
}
