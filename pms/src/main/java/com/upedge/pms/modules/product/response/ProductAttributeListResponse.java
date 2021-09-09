package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductAttributeListRequest;
/**
 * @author gx
 */
public class ProductAttributeListResponse extends BaseResponse {
    public ProductAttributeListResponse(int code, String msg, Object data,ProductAttributeListRequest req) {
        super(code,msg,data,req);
    }
}
