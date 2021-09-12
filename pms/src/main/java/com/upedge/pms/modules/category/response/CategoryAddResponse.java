package com.upedge.pms.modules.category.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.category.request.CategoryAddRequest;
/**
 * @author gx
 */
public class CategoryAddResponse extends BaseResponse {
    public CategoryAddResponse(int code, String msg, Object data, CategoryAddRequest req) {
        super(code,msg,data,req);
    }
}
