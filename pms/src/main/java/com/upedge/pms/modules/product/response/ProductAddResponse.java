package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductAddRequest;
/**
 * @author gx
 */
public class ProductAddResponse extends BaseResponse {
    public ProductAddResponse(int code, String msg, Object data, ProductAddRequest req) {
        super(code,msg,data,req);
    }
}
