package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.StoreProductVariantAddRequest;

/**
 * @author author
 */
public class StoreProductVariantAddResponse extends BaseResponse {
    public StoreProductVariantAddResponse(int code, String msg, Object data, StoreProductVariantAddRequest req) {
        super(code,msg,data,req);
    }
}
