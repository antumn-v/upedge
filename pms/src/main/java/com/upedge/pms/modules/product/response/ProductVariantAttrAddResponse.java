package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductVariantAttrAddRequest;
/**
 * @author gx
 */
public class ProductVariantAttrAddResponse extends BaseResponse {
    public ProductVariantAttrAddResponse(int code, String msg, Object data, ProductVariantAttrAddRequest req) {
        super(code,msg,data,req);
    }
}
