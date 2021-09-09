package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductVariantAttrListRequest;
/**
 * @author gx
 */
public class ProductVariantAttrListResponse extends BaseResponse {
    public ProductVariantAttrListResponse(int code, String msg, Object data,ProductVariantAttrListRequest req) {
        super(code,msg,data,req);
    }
}
