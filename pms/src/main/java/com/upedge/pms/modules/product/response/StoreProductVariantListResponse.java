package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.StoreProductVariantListRequest;

/**
 * @author author
 */
public class StoreProductVariantListResponse extends BaseResponse {
    public StoreProductVariantListResponse(int code, String msg, Object data, StoreProductVariantListRequest req) {
        super(code,msg,data,req);
    }
}
