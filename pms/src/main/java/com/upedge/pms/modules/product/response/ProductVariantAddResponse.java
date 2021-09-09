package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductVariantAddRequest;
/**
 * @author gx
 */
public class ProductVariantAddResponse extends BaseResponse {
    public ProductVariantAddResponse(int code, String msg, Object data, ProductVariantAddRequest req) {
        super(code,msg,data,req);
    }
}
