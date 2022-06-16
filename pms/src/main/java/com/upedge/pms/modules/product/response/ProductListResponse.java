package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductListRequest;
/**
 * @author gx
 */
public class ProductListResponse extends BaseResponse {
    public ProductListResponse(int code, String msg, Object data,ProductListRequest req) {
        super(code,msg,data,req);
    }

}
