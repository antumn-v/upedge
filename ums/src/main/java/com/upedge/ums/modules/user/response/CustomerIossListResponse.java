package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerIossListRequest;
/**
 * @author gx
 */
public class CustomerIossListResponse extends BaseResponse {
    public CustomerIossListResponse(int code, String msg, Object data,CustomerIossListRequest req) {
        super(code,msg,data,req);
    }
}
