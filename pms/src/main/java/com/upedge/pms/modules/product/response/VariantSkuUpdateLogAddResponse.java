package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.VariantSkuUpdateLogAddRequest;
/**
 * @author gx
 */
public class VariantSkuUpdateLogAddResponse extends BaseResponse {
    public VariantSkuUpdateLogAddResponse(int code, String msg, Object data, VariantSkuUpdateLogAddRequest req) {
        super(code,msg,data,req);
    }
}
