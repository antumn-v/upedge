package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductLogAddRequest;
/**
 * @author gx
 */
public class ProductLogAddResponse extends BaseResponse {
    public ProductLogAddResponse(int code, String msg, Object data, ProductLogAddRequest req) {
        super(code,msg,data,req);
    }
}
