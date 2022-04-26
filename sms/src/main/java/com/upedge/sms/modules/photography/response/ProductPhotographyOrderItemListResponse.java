package com.upedge.sms.modules.photography.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderItemListRequest;
/**
 * @author gx
 */
public class ProductPhotographyOrderItemListResponse extends BaseResponse {
    public ProductPhotographyOrderItemListResponse(int code, String msg, Object data,ProductPhotographyOrderItemListRequest req) {
        super(code,msg,data,req);
    }
}
