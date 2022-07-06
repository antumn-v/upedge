package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.VariantSkuUpdateLogListRequest;
/**
 * @author gx
 */
public class VariantSkuUpdateLogListResponse extends BaseResponse {
    public VariantSkuUpdateLogListResponse(int code, String msg, Object data,VariantSkuUpdateLogListRequest req) {
        super(code,msg,data,req);
    }
}
