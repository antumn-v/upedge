package com.upedge.sms.modules.photography.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.photography.request.ProductPhotographyOrderAddRequest;
/**
 * @author gx
 */
public class ProductPhotographyOrderAddResponse extends BaseResponse {
    public ProductPhotographyOrderAddResponse(int code, String msg, Object data, ProductPhotographyOrderAddRequest req) {
        super(code,msg,data,req);
    }
}
