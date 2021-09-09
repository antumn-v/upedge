package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.CustomerPrivateProductListRequest;
/**
 * @author gx
 */
public class CustomerPrivateProductListResponse extends BaseResponse {
    public CustomerPrivateProductListResponse(int code, String msg, Object data,CustomerPrivateProductListRequest req) {
        super(code,msg,data,req);
    }
}
