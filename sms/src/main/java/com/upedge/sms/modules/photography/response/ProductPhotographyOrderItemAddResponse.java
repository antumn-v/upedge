package com.upedge.sms.modules.photography.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderItemAddRequest;
/**
 * @author gx
 */
public class ProductPhotographyOrderItemAddResponse extends BaseResponse {
    public ProductPhotographyOrderItemAddResponse(int code, String msg, Object data, ProductPhotographyOrderItemAddRequest req) {
        super(code,msg,data,req);
    }
}
