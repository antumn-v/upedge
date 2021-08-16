package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerListRequest;
/**
 * @author gx
 */
public class CustomerListResponse extends BaseResponse {
    public CustomerListResponse(int code, String msg, Object data,CustomerListRequest req) {
        super(code,msg,data,req);
    }
}
