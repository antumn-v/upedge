package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.CategoryListRequest;

/**
 * @author author
 */
public class CategoryListResponse extends BaseResponse {
    public CategoryListResponse(int code, String msg, Object data, CategoryListRequest req) {
        super(code,msg,data,req);
    }
}
