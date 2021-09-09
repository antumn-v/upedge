package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ProductImgListRequest;
/**
 * @author gx
 */
public class ProductImgListResponse extends BaseResponse {
    public ProductImgListResponse(int code, String msg, Object data,ProductImgListRequest req) {
        super(code,msg,data,req);
    }
}
