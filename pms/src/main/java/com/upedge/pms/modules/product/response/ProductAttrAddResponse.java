package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductAttrAddRequest;
/**
 * @author gx
 */
public class ProductAttrAddResponse extends BaseResponse {
    public ProductAttrAddResponse(int code, String msg, Object data, ProductAttrAddRequest req) {
        super(code,msg,data,req);
    }
}
