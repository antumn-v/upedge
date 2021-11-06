package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.CategoryAddRequest;

/**
 * @author author
 */
public class CategoryAddResponse extends BaseResponse {
    public CategoryAddResponse(int code, String msg, Object data, CategoryAddRequest req) {
        super(code,msg,data,req);
    }
}
