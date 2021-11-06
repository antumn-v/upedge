package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.AliCnCategoryListRequest;

/**
 * @author author
 */
public class AliCnCategoryListResponse extends BaseResponse {
    public AliCnCategoryListResponse(int code, String msg, Object data, AliCnCategoryListRequest req) {
        super(code,msg,data,req);
    }
}
