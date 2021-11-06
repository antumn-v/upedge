package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.StoreProductAttributeAddRequest;

/**
 * @author author
 */
public class StoreProductAttributeAddResponse extends BaseResponse {
    public StoreProductAttributeAddResponse(int code, String msg, Object data, StoreProductAttributeAddRequest req) {
        super(code,msg,data,req);
    }
}
