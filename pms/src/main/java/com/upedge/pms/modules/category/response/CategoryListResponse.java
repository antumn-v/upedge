package com.upedge.pms.modules.category.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.category.request.CategoryListRequest;
/**
 * @author gx
 */
public class CategoryListResponse extends BaseResponse {
    public CategoryListResponse(int code, String msg, Object data,CategoryListRequest req) {
        super(code,msg,data,req);
    }
}
