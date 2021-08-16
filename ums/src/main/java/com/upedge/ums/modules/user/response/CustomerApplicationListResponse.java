package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerApplicationListRequest;
/**
 * @author gx
 */
public class CustomerApplicationListResponse extends BaseResponse {
    public CustomerApplicationListResponse(int code, String msg, Object data,CustomerApplicationListRequest req) {
        super(code,msg,data,req);
    }
}
