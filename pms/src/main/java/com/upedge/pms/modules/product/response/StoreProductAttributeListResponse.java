package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.StoreProductAttributeListRequest;

/**
 * @author author
 */
public class StoreProductAttributeListResponse extends BaseResponse {
    public StoreProductAttributeListResponse(int code, String msg, Object data, StoreProductAttributeListRequest req) {
        super(code,msg,data,req);
    }
}
