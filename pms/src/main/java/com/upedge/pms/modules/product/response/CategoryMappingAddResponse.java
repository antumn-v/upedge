package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.CategoryMappingAddRequest;

/**
 * @author author
 */
public class CategoryMappingAddResponse extends BaseResponse {
    public CategoryMappingAddResponse(int code, String msg, Object data, CategoryMappingAddRequest req) {
        super(code,msg,data,req);
    }
}
