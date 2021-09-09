package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductAttrListRequest;
/**
 * @author gx
 */
public class ProductAttrListResponse extends BaseResponse {
    public ProductAttrListResponse(int code, String msg, Object data,ProductAttrListRequest req) {
        super(code,msg,data,req);
    }
}
