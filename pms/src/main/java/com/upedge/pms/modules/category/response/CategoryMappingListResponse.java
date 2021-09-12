package com.upedge.pms.modules.category.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.category.request.CategoryMappingListRequest;
/**
 * @author gx
 */
public class CategoryMappingListResponse extends BaseResponse {
    public CategoryMappingListResponse(int code, String msg, Object data,CategoryMappingListRequest req) {
        super(code,msg,data,req);
    }
}
