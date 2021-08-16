package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerApplicationAddRequest;
/**
 * @author gx
 */
public class CustomerApplicationAddResponse extends BaseResponse {
    public CustomerApplicationAddResponse(int code, String msg, Object data, CustomerApplicationAddRequest req) {
        super(code,msg,data,req);
    }
}
