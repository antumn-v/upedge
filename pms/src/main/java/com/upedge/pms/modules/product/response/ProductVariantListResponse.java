package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductVariantListRequest;
/**
 * @author gx
 */
public class ProductVariantListResponse extends BaseResponse {
    public ProductVariantListResponse(int code, String msg, Object data,ProductVariantListRequest req) {
        super(code,msg,data,req);
    }
}
