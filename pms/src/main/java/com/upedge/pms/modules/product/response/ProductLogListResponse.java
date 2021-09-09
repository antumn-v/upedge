package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductLogListRequest;
/**
 * @author gx
 */
public class ProductLogListResponse extends BaseResponse {
    public ProductLogListResponse(int code, String msg, Object data,ProductLogListRequest req) {
        super(code,msg,data,req);
    }
}
