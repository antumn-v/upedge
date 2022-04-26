package com.upedge.sms.modules.photography.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderListRequest;
/**
 * @author gx
 */
public class ProductPhotographyOrderListResponse extends BaseResponse {
    public ProductPhotographyOrderListResponse(int code, String msg, Object data,ProductPhotographyOrderListRequest req) {
        super(code,msg,data,req);
    }
}
