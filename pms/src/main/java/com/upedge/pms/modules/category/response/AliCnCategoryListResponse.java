package com.upedge.pms.modules.category.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.category.request.AliCnCategoryListRequest;
/**
 * @author gx
 */
public class AliCnCategoryListResponse extends BaseResponse {
    public AliCnCategoryListResponse(int code, String msg, Object data,AliCnCategoryListRequest req) {
        super(code,msg,data,req);
    }
}
