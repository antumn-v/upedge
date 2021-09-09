package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductInfoAddRequest;
/**
 * @author gx
 */
public class ProductInfoAddResponse extends BaseResponse {
    public ProductInfoAddResponse(int code, String msg, Object data, ProductInfoAddRequest req) {
        super(code,msg,data,req);
    }
}
