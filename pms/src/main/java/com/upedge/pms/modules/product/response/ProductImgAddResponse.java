package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductImgAddRequest;
/**
 * @author gx
 */
public class ProductImgAddResponse extends BaseResponse {
    public ProductImgAddResponse(int code, String msg, Object data, ProductImgAddRequest req) {
        super(code,msg,data,req);
    }
}
