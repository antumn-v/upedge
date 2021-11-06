package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.CategoryMappingListRequest;

/**
 * @author author
 */
public class CategoryMappingListResponse extends BaseResponse {
    public CategoryMappingListResponse(int code, String msg, Object data, CategoryMappingListRequest req) {
        super(code,msg,data,req);
    }
}
