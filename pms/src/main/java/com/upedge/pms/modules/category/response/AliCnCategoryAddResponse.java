package com.upedge.pms.modules.category.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.category.request.AliCnCategoryAddRequest;
/**
 * @author gx
 */
public class AliCnCategoryAddResponse extends BaseResponse {
    public AliCnCategoryAddResponse(int code, String msg, Object data, AliCnCategoryAddRequest req) {
        super(code,msg,data,req);
    }
}
