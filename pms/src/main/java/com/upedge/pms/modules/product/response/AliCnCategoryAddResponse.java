package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.AliCnCategoryAddRequest;

/**
 * @author author
 */
public class AliCnCategoryAddResponse extends BaseResponse {
    public AliCnCategoryAddResponse(int code, String msg, Object data, AliCnCategoryAddRequest req) {
        super(code,msg,data,req);
    }
}
