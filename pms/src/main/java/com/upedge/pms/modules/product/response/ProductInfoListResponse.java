package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductInfoListRequest;
/**
 * @author gx
 */
public class ProductInfoListResponse extends BaseResponse {
    public ProductInfoListResponse(int code, String msg, Object data,ProductInfoListRequest req) {
        super(code,msg,data,req);
    }
}
