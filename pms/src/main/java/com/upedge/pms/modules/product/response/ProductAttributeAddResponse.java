package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductAttributeAddRequest;
/**
 * @author gx
 */
public class ProductAttributeAddResponse extends BaseResponse {
    public ProductAttributeAddResponse(int code, String msg, Object data, ProductAttributeAddRequest req) {
        super(code,msg,data,req);
    }
}
