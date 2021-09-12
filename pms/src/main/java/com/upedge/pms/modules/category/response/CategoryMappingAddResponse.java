package com.upedge.pms.modules.category.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.category.request.CategoryMappingAddRequest;
/**
 * @author gx
 */
public class CategoryMappingAddResponse extends BaseResponse {
    public CategoryMappingAddResponse(int code, String msg, Object data, CategoryMappingAddRequest req) {
        super(code,msg,data,req);
    }
}
